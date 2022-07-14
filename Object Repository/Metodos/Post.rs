<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Post</name>
   <tag></tag>
   <elementGuidId>1b9e40cc-af15-49cb-bfdf-4f3b53116b2a</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;${Body}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>4312e2ea-21d1-4295-8533-ab816923774e</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${GlobalVariable.ejecucion.token}</value>
      <webElementGuid>8a9e2e5f-2387-47a5-81d4-37cc41566036</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>X-B3-TraceId</name>
      <type>Main</type>
      <value>${GlobalVariable.ejecucion.InterOperable}</value>
      <webElementGuid>4949d128-1aa1-405d-b858-d7d023566749</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>X-B3-SpanId</name>
      <type>Main</type>
      <value>${GlobalVariable.ejecucion.InterOperable}</value>
      <webElementGuid>e205a70d-580e-49ca-a283-af7581fdb80a</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>8.0.1</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${Endpoint}</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>14c96ca6-4e0c-4554-9395-351c925fe816</id>
      <masked>false</masked>
      <name>Endpoint</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>b2224604-9224-4510-b5dc-c01c0685dea5</id>
      <masked>false</masked>
      <name>Body</name>
   </variables>
   <variables>
      <defaultValue>[:]</defaultValue>
      <description></description>
      <id>474d56c1-7e4b-4cac-922e-c9515689c724</id>
      <masked>false</masked>
      <name>RespuestaEsperada</name>
   </variables>
   <verificationScript>import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()
ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()
String responseText = response.getResponseText()

def variables = request.getVariables()
Map RespuestaEsperada = variables.get('RespuestaEsperada')
String Body = variables.get('Body')
String Endpoint = variables.get('Endpoint')
Map StatusCode = [&quot;Recibido&quot;:response.getStatusCode(), &quot;Esperado&quot;: RespuestaEsperada.StatusCode]

WebUI.callTestCase(findTestCase('3.- Puntos de Control/1.- Response/1.- Validate'),
	[	('Endpoint') : Endpoint,
		('Headers') : [:],
		('Enviado') : Body,
		('Esperado') : RespuestaEsperada.Mensaje,
		('Obtenido') : responseText,
		('StatusCode') : StatusCode],
	FailureHandling.STOP_ON_FAILURE)

def jsonSlurper = new JsonSlurper()
def jResponse = jsonSlurper.parseText(responseText)

WebUI.callTestCase(findTestCase('3.- Puntos de Control/1.- Response/2.- Catch')
	, [('Response') : jResponse]
	, FailureHandling.STOP_ON_FAILURE)
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
