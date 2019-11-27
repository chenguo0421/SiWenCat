# !/usr/bin/env python3
# _*_ coding: utf-8 _*_
import sys
import os
import re
import xml.dom.minidom as Dom


class AnnotationBean:

    def __init__(self, clsPath:str="", clsAnnotation:list=[], methodAnnotation:list=[]):
        self.clsPath = clsPath
        self.clsAnnotation = clsAnnotation
        self.methodAnnotation = methodAnnotation


    def _clsPath(self,clsPath):
        self.clsPath = clsPath
        return self


    def _clsAnnotation(self,clsAnnotation):
        self.clsAnnotation = clsAnnotation
        return self


    def _methodAnnotation(self,methodAnnotation):
        self.methodAnnotation = methodAnnotation
        return self





# 读取android根目录下的setting.gradle文件，选出所有的include的module，生成列表
def get_module_name(rootPath):
    # 打开android根目录下的setting.gradle文件
    setPath = os.path.join(rootPath, "settings.gradle")
    if not os.path.exists(setPath):
       print("file path %s is not exist" % setPath)
       return None
    print("setting.gradle path is %s" %  setPath)
    with open(setPath, mode="r", encoding="utf-8") as rf:
        lines = rf.readlines()
    # with open(setPath, mode="w", encoding="utf-8") as wf:
    #     pass
    _mod_list = []
    for line in lines:
        # print("line = %s" % line)
        # 查找不以双斜杠开头的module，即注释了的不要
        if not re.match('^//',line.lstrip()):
           d = re.findall('\':(.*?)\'',line)
           for each in d:
               _mod_list.append(each)
           d_d = re.findall('\":(.*?)\"',line)
           for each in d_d:
               _mod_list.append(each)
    return _mod_list


# 对所有module的java类进行匹配，是否包含指定的注解内容，若包含，返回该类的路径
def get_module_class_path(list,rootPath):
    _class_path_list = []
    # 循环所有module
    for item in list:
        _absPath = os.path.join(rootPath,item)
        _class_path_list.append(os.path.join(_absPath,"src/main/java"))
    return _class_path_list


# 扫描文件夹下某类型文件
def scan_file_by_suffix(filePath,suffix):
    fileList = []
    print("开始扫描[{0}]".format(filePath))
    if not os.path.isdir(filePath): # # 判断是否是目录
       print("{0} 不是目录".format(filePath))
    else:
       for filename in os.listdir(filePath):   # # 遍历子目录
           if os.path.isdir((filePath + "/" + filename)):
              fileList.extend(scan_file_by_suffix(filePath + "/" + filename,suffix))
           else:
              for suf in suffix:
                  if filename.endswith(suf):
                     print(filename)
                     fileList.append(filePath + "/" + filename)

    return fileList


# 正则表达匹配某一个文件的内容
def regLine(f):
    print("开始打开[{0}]文件".format(f))
    bean = AnnotationBean()
    _r_list = []
    _m_list = []
    with open(f, mode="r", encoding="utf-8") as rf:
        lines = rf.readlines()
        for line in lines:
            # 查找不以双斜杠开头的module，即注释了的不要
            if not re.match('^//',line.lstrip()):
                print("line = %s" % line.lstrip())
                if re.match('@CRouter',line.lstrip()):
                    print("匹配到@CRouter")
                    r = re.findall('@CRouter\((.*?)\)',line.lstrip())
                    for each in r:
                        if re.search('=',each):
                            _r_list.append(each.split("=", 1)[1])
                        else:
                            _r_list.append(each)
                    bean._clsPath(f.split("src/main/java/")[1])
                    bean._clsAnnotation(_r_list.copy())
                    print(_r_list)
                if re.match('@CMethod',line.lstrip()):
                    print("匹配到@CMethod")
                    m = re.findall('@CMethod\((.*?)\)',line.lstrip())
                    for each in m:
                        if re.search('=',each):
                            _m_list.append(each.split("=", 1)[1])
                        else:
                            _m_list.append(each)
                    bean._clsPath(f.split("src/main/java/")[1])
                    bean._methodAnnotation(_m_list.copy())
                    print(_m_list)
    return bean


# 扫描每一个classpath，对每个java文件进行匹配，看有无指定注解，若有，返回该类的路径
def get_annotation_path_list(list):
    _annotation_path_list = []
    for item in list:
        _fileList = scan_file_by_suffix(item,[".java",".kt"])
        for f in _fileList:
            bean = regLine(f)
            if not "" == bean.clsPath:
               _annotation_path_list.append(bean)
    return _annotation_path_list


# 根据_annotationPathList生成CRouter.xml文件
def write(xmlPath,list):
    print("Router.xml文件路径为：[{0}]".format(xmlPath))
    if os.path.exists(xmlPath) and os.path.isfile(xmlPath):
       os.remove(xmlPath)
    # 在内存中创建一个空的文档
    doc = Dom.Document()
    # 创建一个根节点Managers对象
    root = doc.createElement('CRouters')
    # 将根节点添加到文档对象中
    doc.appendChild(root)
    for bean in list:
        nodeCRouter = doc.createElement('CRouter')
        # 由于一个类只有一个路由，这里直接取第0个
        if len(bean.clsAnnotation) > 0:
            nodeRouterPath = doc.createElement('RouterPath')
            # 给叶子节点RouterPath设置一个文本节点，用于显示文本内容
            nodeRouterPath.appendChild(doc.createTextNode(str(bean.clsAnnotation[0])))
            nodeCRouter.appendChild(nodeRouterPath)

        nodeClassPath = doc.createElement("ClassPath")
        nodeClassPath.appendChild(doc.createTextNode(str(bean.clsPath)))


        nodeCRouter.appendChild(nodeClassPath)

        if len(bean.methodAnnotation) > 0:
            nodeMethodPath = doc.createElement("MethodPath")
            for mPath in bean.methodAnnotation:
                nodeMethodPathItem = doc.createElement("MethodPathItem")
                nodeMethodPathItem.appendChild(doc.createTextNode(mPath))
                nodeMethodPath.appendChild(nodeMethodPathItem)
            nodeCRouter.appendChild(nodeMethodPath)


        root.appendChild(nodeCRouter)
    # 开始写xml文档
    fp = open(xmlPath, 'w')
    doc.writexml(fp, indent='\t', addindent='\t', newl='\n', encoding="utf-8")
    fp.close()


# 本方法主要去除routerPath的双引号和空格
def rebuild_annotationBean_list(list):
    new_list = []
    for item in list:
        bean = AnnotationBean()
        _routerPathList = []
        _methodPathList = []
        path = item.clsPath
        path = path.split(".")[0]
        path = re.sub("/",".",path)
        path = re.sub(r"\\",".",path)
        print("path = %s" % path)
        for routerPath in item.clsAnnotation:
            routerPath.strip()
            routerPath = re.sub("\"","",routerPath)
            routerPath = re.sub("\'","",routerPath)
            _routerPathList.append(routerPath.lstrip())
        bean._clsPath(path.lstrip())
        bean._clsAnnotation(_routerPathList)
        for methodPath in item.methodAnnotation:
            methodPath.strip()
            methodPath = re.sub("\"","",methodPath)
            methodPath = re.sub("\'","",methodPath)
            _methodPathList.append(methodPath.lstrip())
        bean._methodAnnotation(_methodPathList)
        new_list.append(bean)
    return new_list



#生成CRouter.xml
def build_router_xml():
    if _annotationBeanList == None or len(_annotationBeanList) <= 0:
        return
    try:
        appModulePath = os.getcwd()
        print("当前路径[{0}]".format(appModulePath))
        assetsPath = os.path.join(appModulePath,"src/main/assets")
        if not os.path.exists(assetsPath):
           print("准备生成assets文件夹")
           os.makedirs(assetsPath)
        xmlPath = os.path.join(assetsPath,"CRouter.xml")
        write(xmlPath,_annotationBeanList)
        return True
    except Exception:
           print("复制dist文件失败")
    return False


# 获取项目根路径 D:\Project\2018_10_26\video_new\android
_rootPath = sys.argv[1]

# 获取该项目由多少个module，从setting.gradle中获取
_modList = get_module_name(_rootPath)


# 将module路径转换为module下的java文件夹下
_classPathList = get_module_class_path(_modList,_rootPath)


# 扫描每一个classpath，对每个java文件进行匹配，看有无指定注解，若有，返回该类的路径
_annotationBeanList = get_annotation_path_list(_classPathList)

# 重新构建annotationBean中的不规范数据，例如路径的\和/的不统一，routerPath含有双引号
_annotationBeanList = rebuild_annotationBean_list(_annotationBeanList)

# 根据AnnotationBean生成Router.xml配置文件,放在主module的assets文件夹下
build_router_xml()







