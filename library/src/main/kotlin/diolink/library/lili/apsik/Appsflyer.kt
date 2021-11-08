package diolink.library.lili.apsik

interface Appsflyer {

    fun onConversionDataSuccess(data: MutableMap<String, Any>?, url: String)

    fun onConversionDataFail(error: String?)

}