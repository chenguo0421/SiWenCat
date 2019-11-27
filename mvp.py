# !/usr/bin/env python3
# _*_ coding: utf-8 _*_
import sys
import os
import re
import xml.dom.minidom as Dom


def doNext(_rootPath,_templateFilePath,_packagePath,_baseName,_type):
    print("start create mvp file, please wait...")

    # 处理mvp所在包路径，假如传入的路径是D:\Project\BaseWorkspace\CBase_Kotlin\app\src\main\java\cn\com\cg\testmvp
    # 则处理成"cn\com\cg\testmvp"
    _packagePath = disposePath(_packagePath)
    print("dispose packagePath as %s" % _packagePath)

    # 获取该项目由多少个module，从setting.gradle中获取
    _modList = get_module_name(_rootPath)

    # 将module路径转换为module下的java文件夹下
    _classPathList = get_module_class_path(_modList,_rootPath)
    print("get_module_class_path success list = %s" % _classPathList)

    # 找到mvp包路径属于哪个module
    _srcPath = scanMatchedPath(_classPathList,_packagePath)

    if len(_srcPath) != 1:
       print("could not find mvp path in anyOne module,please check mvp.py code")
       quit()

    # 获取mvp在机器上的全路径
    _tempPath = os.path.join(_rootPath,_srcPath[0])
    print("mvp temp path is %s" % _tempPath)
    _fullPath = os.path.join(_tempPath,_packagePath)
    print("mvp full path is %s" % _fullPath)

    # 处理mvp类名 若不是首字母大写，处理成首字母大写，假如传入login，处理成Login
    _baseName = disposeName(_baseName)
    print("dispose name as %s" % _baseName)

    # 从模板文件夹复制文件到mvp在机器上的全路径上
    cpOK = cpFile(_templateFilePath,_fullPath,_type)

    if not cpOK:
       print("copy template file to mvp file failed,please check mvp.py code")
       quit()

    # 拷贝文件成功后，替换各文件内部指定内容

    reWriteFileOK = reWriteMVPFile(_fullPath,changePathConnectorType(_packagePath),_baseName,_type)
    if not reWriteFileOK:
       print("reWrite mvp file failed,please check mvp.py code")
       quit()

    print("create mvp file ok,please Synchronize project")






def disposePath(_packagePath):
    tempPath = changePathConnectorType(_packagePath)
    print("tempPath = %s" % tempPath)
    listPath = tempPath.split("src.main.java.")
    length = len(listPath)
    if len(listPath) > 1:
       path = listPath[len(listPath) - 1]
       return path.replace(".","\\")
    else:
       return tempPath.replace(".","\\")


# 驼峰式命名，首字母大写
def disposeName(_baseName):
    if _baseName.istitle():
        return _baseName
    return _baseName.capitalize()


# 扫描当前需要再那个module的那个包下创建mvp
def scanMatchedPath(_classPathList,_packagePath):
    javaPathList = []
    for javaPath in _classPathList:
        mvpPath = os.path.join(javaPath,_packagePath)
        mvpPath = mvpPath.replace("/","\\")
        print("mvp path = %s" % mvpPath)
        if os.path.exists(mvpPath) and os.path.isdir(mvpPath):
           javaPathList.append(javaPath)
    return javaPathList


# 将模板MVP拷贝到指定路径下
def cpFile(_templateFilePath,_fullPath,_type):
    copy_file(_templateFilePath,_fullPath)
    if "activity" == _type:
       #若当前创建类型为Activity类型MVP，则删除已拷贝过来的Fragment文件
       os.remove(os.path.join(_fullPath,"view","fragment","TestFragment"))
    else:
       #若当前创建类型为Fragment类型MVP，则删除已拷贝过来的Activity文件
       os.remove(os.path.join(_fullPath,"view","TestActivity"))
    return True


# 修改已拷贝到mvp路径下的各个文件内容
def reWriteMVPFile(_fullPath,packagePath,_baseName,_type):
    if "activity" == _type:
        reWrite(os.path.join(_fullPath,"contract"),os.path.join(_fullPath,"contract","TestContract"),packagePath,_baseName,1)
        reWrite(os.path.join(_fullPath,"model"),os.path.join(_fullPath,"model","TestModel"),packagePath,_baseName,2)
        reWrite(os.path.join(_fullPath,"presenter"),os.path.join(_fullPath,"presenter","TestPresenter"),packagePath,_baseName,3)
        reWrite(os.path.join(_fullPath,"view"),os.path.join(_fullPath,"view","TestActivity"),packagePath,_baseName,4)
    else:
        reWrite(os.path.join(_fullPath,"contract"),os.path.join(_fullPath,"contract","TestContract"),packagePath,_baseName+"FM",1)
        reWrite(os.path.join(_fullPath,"model"),os.path.join(_fullPath,"model","TestModel"),packagePath,_baseName+"FM",2)
        reWrite(os.path.join(_fullPath,"presenter"),os.path.join(_fullPath,"presenter","TestPresenter"),packagePath,_baseName+"FM",3)
        reWrite(os.path.join(_fullPath,"view","fragment"),os.path.join(_fullPath,"view","fragment","TestFragment"),packagePath,_baseName,5)
    return True


# 修改文件内容及文件名，实际上是将源文件删除，换成了新文件
def reWrite(_parentPath,_file,_packagePath,_baseName,type):
    _targetFilePath = None
    if 1 == type:
       _targetFilePath = os.path.join(_parentPath,_baseName+"Contract.kt")
    elif 2 == type:
       _targetFilePath = os.path.join(_parentPath,_baseName+"Model.kt")
    elif 3 == type:
       _targetFilePath = os.path.join(_parentPath,_baseName+"Presenter.kt")
    elif 4 == type:
       _targetFilePath = os.path.join(_parentPath,_baseName+"Activity.kt")
    elif 5 == type:
       _targetFilePath = os.path.join(_parentPath,_baseName+"Fragment.kt")

    print("targetFilePath = %s" % _targetFilePath)

    if os.path.exists(_targetFilePath) and os.path.isfile(_targetFilePath):
        print("targetFilePath is exists")
        print("targetFilePath = %s" % _targetFilePath)
    else:
        fd = open(_targetFilePath, mode="w", encoding="utf-8")
        fd.close()

    with open(_file, "r", encoding="utf-8") as f1,open(_targetFilePath , "w", encoding="utf-8") as f2:
         for lin in f1:
             print(lin)
             if "%Path" in lin:
                lin = lin.replace("%Path", _packagePath)
             if "%Name" in lin:
                lin = lin.replace("%Name", _baseName)
             f2.write(lin)
         f1.close()
         f2.close()
         if not _targetFilePath == None:
            os.remove(_file)


# 将文件路径的分隔符替换为点号
def changePathConnectorType(path):
    #将路径处理为以.连接
    _tempPath = path.replace("/",".")
    _tempPath = _tempPath.replace("\\",".")
    _tempPath = _tempPath.replace("\\\\",".")
    _tempPath = _tempPath.replace("//",".")
    _tempPath = _tempPath.replace("\/",".")
    _tempPath = _tempPath.replace("/\\",".")
    return _tempPath


# 将一个文件夹的内容拷贝到另一个文件夹下
def copy_file(sourcePath,targetPath):
    if not os.path.exists(sourcePath):
        return
    if not os.path.exists(targetPath):
        os.makedirs(targetPath)
    for fileName in os.listdir(sourcePath):
        absourcePath = os.path.join(sourcePath, fileName)
        # 拼接目标文件或者文件加的绝对路径
        abstargetPath = os.path.join(targetPath, fileName)
        # 判断原文件的绝对路径是目录还是文件
        if os.path.isdir(absourcePath):
            # 是目录就创建相应的目标目录
            #os.makedirs(abstargetPath)
            # 递归调用getDirAndCopyFile()函数
            copy_file(absourcePath, abstargetPath)
        # 是文件就进行复制
        if os.path.isfile(absourcePath):
            rbf = open(absourcePath, "rb")
            wbf = open(abstargetPath, "wb")
            while True:
                content = rbf.readline(1024 * 1024)
                if len(content) == 0:
                    break
                wbf.write(content)
                wbf.flush()
            rbf.close()
            wbf.close()




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



# 通过脚本方式，自动创建MVP的View，Presenter，Model，Contract以及关联关系，并初始化一些方法


# 配置项目根路径 D:\Project\BaseWorkspace\CBase_Kotlin
_rootPath = 'D:\\Project\\BaseWorkspace\\CBase_Kotlin'

# 获取命令行入参
# mvp所在包路径
_packagePath = sys.argv[1]
# mvp类名
_baseName = sys.argv[2]
# mvp服务类型，activity或fragment
_type = sys.argv[3]

print("root path is %s" %  _rootPath)
print("package path is %s" %  _packagePath)
print("baseName is %s" %  _baseName)
print("mvp type is %s" %  _type)



# 判断模板是否存在，若不存在直接终止脚本
_templateFilePath = os.path.join(_rootPath,"template")
if not os.path.exists(_templateFilePath):
   print("not find template int rootPath,sure has added template file")
   quit()


# 处理mvp服务类型，activity或fragment，若不是此两种，则直接停止脚本
_type = _type.lower()
if 'activity' == _type or 'fragment' == _type:
    doNext(_rootPath,_templateFilePath,_packagePath,_baseName,_type)
else:
   print("not support other type, please use 'activity' or 'fragment' as the type")
   quit()











