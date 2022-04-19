import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling

'Genero ID para Credin'
WebUI.callTestCase(findTestCase('2.- Patrones de Ejecucion/3.- Body/2.- Generar ID/2.- ori_trx_id'), [('Body') : Body], 
    FailureHandling.STOP_ON_FAILURE)

'Genero ID para QR'
WebUI.callTestCase(findTestCase('2.- Patrones de Ejecucion/3.- Body/2.- Generar ID/3.- qr_id_trx'), [('Body') : Body], 
    FailureHandling.STOP_ON_FAILURE)

'Cargo el ID Hash del debin obtenido'
WebUI.callTestCase(findTestCase('2.- Patrones de Ejecucion/3.- Body/2.- Generar ID/1.- Insertar ID'), [('Body') : Body], 
    FailureHandling.STOP_ON_FAILURE)

return Body

