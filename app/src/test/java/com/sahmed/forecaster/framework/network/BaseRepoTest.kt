package com.sahmed.forecaster.framework.network

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class BaseRepoTest {


    @Mock
    lateinit var baseCallback: BaseCallback

    lateinit var baseRepo: BaseRepo

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        baseRepo = Mockito.spy(BaseRepo::class.java)
    }

    @Test
    fun checkCallbackInCaseUnknownHostException (){

        var unknownHostExc = Mockito.mock(UnknownHostException::class.java)

        baseRepo.handleErrorResponse(unknownHostExc,baseCallback)

        Mockito.verify(baseCallback,Mockito.times(1)).onNetworkIssue()
    }

    @Test
    fun checkCallbackInCaseSocketTimeOutException (){

        var unknownHostExc = Mockito.mock(SocketTimeoutException::class.java)

        baseRepo.handleErrorResponse(unknownHostExc,baseCallback)

        Mockito.verify(baseCallback,Mockito.times(1)).onNetworkIssue()
    }

    @Test
    fun checkCallBackInCaseGeneralFailure(){
        var otherException = Mockito.mock(Exception::class.java)
        baseRepo.handleErrorResponse(otherException,baseCallback)

        Mockito.verify(baseCallback,Mockito.times(1)).onFailure()
    }
}