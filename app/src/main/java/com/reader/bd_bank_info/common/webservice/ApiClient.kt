package com.reader.bd_bank_info.common.webservice

interface ApiClient {

    fun <Service> createService(serviceClass: Class<Service>): Service
}
