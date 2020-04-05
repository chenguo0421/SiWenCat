package cn.com.cg.cache

object Lruchche {
    var cacheMap:LinkedHashMap<String,Class<*>>? = LinkedHashMap(20)

    fun put(key:String,value:Class<*>){
        val iterator = cacheMap?.entries?.iterator()
        var firstKey:String? = null
        if (iterator!!.hasNext()){
            firstKey = iterator?.next()?.key
        }

        if (firstKey != null && cacheMap?.size!! >= 20) {
            cacheMap?.remove(firstKey)
        }
        cacheMap!![key] = value
    }

    fun get(key:String): Class<*>? {
        val value = cacheMap?.remove(key)
        if (value != null) {
            cacheMap!![key] = value
        }
        return cacheMap!![key]
    }

}