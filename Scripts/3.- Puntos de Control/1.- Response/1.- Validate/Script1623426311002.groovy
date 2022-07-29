import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper

def jsonSlurper = new JsonSlurper()

if (!(Enviado.equals('')) && Enviado.contains('{')) {
    Enviado = jsonSlurper.parseText(Enviado)
}

List errores = []

if (StatusCode.Esperado != StatusCode.Recibido) {
    errores += ["No coinciden los Status Code Esperado: $StatusCode.Esperado | Recibido: $StatusCode.Recibido"]
}

Esperado = jsonSlurper.parseText(Esperado)

if (Obtenido.equals('Error de firma de mensaje. "ERROR ERROR DE FIRMA: "')) {
    errores += ['Error de firma de mensaje. "ERROR ERROR DE FIRMA: "']
} else {
    Obtenido = jsonSlurper.parseText(Obtenido)

	try {
		errores = coelsa.Util.validar(Obtenido, Esperado)		
	} catch (Exception e) {
		String error = e.toString()
		
		if(e.message.equals("Cannot invoke method get() on null object")) {
			errores += [" No se encontro lo esperado en lo recibido"] 		
		}
		
		errores += [error]
	}
}

Map bd = WebUI.callTestCase(findTestCase('3.- Puntos de Control/2.- Data Base/1.- Validate DDBB'), [('response') : Obtenido],
	FailureHandling.STOP_ON_FAILURE)
if(bd == null) {
	bd = [:]
	bd.db = ""
	//bd.errores = ["Error al validar base de datos"]
	bd.errores = []
}

errores += bd.errores

def evidencia = [('Endpoint') : Endpoint, ('Headers') : Headers, ('Enviado') : Enviado, ('Obtenido') : Obtenido, ('Esperado') : Esperado
    , ('StatusCode') : StatusCode, ('Base_datos') : bd.db, ('Errores') : errores]

coelsa.Log.writeAccion(evidencia)

if (errores.size() > 0) {
    KeywordUtil.markFailed('Se encontraron diferencias entre lo esperado y lo recibido')
} else {
    coelsa.Log.changeStatusCase('Passed')
}
