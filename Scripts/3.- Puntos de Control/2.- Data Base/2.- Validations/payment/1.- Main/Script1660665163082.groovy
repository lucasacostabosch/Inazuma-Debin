import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

String  Accion = GlobalVariable.Accion

Map dato = [db:[:],errores:[]]

'Validar datos de body contra DDBB'
Map dato1 = WebUI.callTestCase(findTestCase('3.- Puntos de Control/2.- Data Base/2.- Validations/'+ Accion + '/2.- Validate Body'), 
    [('response') : response], FailureHandling.STOP_ON_FAILURE)

'Validar datos del request contra DDBB'
//Map dato2 = WebUI.callTestCase(findTestCase('3.- Puntos de Control/2.- Data Base/2.- Validations/'+ Accion + '/3.- Validate Response'),
	//[('response') : response], FailureHandling.STOP_ON_FAILURE)

dato.db = dato1.db 
dato.errores = dato1.errores

//dato.db = dato1.db + dato2.db
//dato.errores = dato1.errores + dato2.errores

return dato