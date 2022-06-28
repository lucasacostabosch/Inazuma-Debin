import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

String  Accion = GlobalVariable.Accion
String TestCase = "3.- Puntos de Control/2.- Data Base/2.- Validations/$Accion/1.- Main"

def dato = [('db'): [:], ('errores'): []]
/*
try {
	dato = WebUI.callTestCase(findTestCase(TestCase),
		[('response') : response], FailureHandling.STOP_ON_FAILURE)
}
catch (Exception e) {
	String message = "Cannot find test case \'Test Cases/$TestCase\'"
	if (e.message.equals(message)) {
		dato = [
			('db'): "No existen validaciones de DB para la Accion: \'$Accion\'",
			('errores'): []
			]
	} else {
		throw e
	}
}
*/
return dato
