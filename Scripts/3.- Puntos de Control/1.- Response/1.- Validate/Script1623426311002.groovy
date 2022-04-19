import groovy.json.JsonSlurper
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil

def jsonSlurper = new JsonSlurper()
if(!Enviado.equals(""))
	Enviado = jsonSlurper.parseText(Enviado)
	
List errores = []

if(StatusCode.Esperado != StatusCode.Recibido)
	errores += ["No coinciden los Status Code Esperado: ${StatusCode.Esperado} | Recibido: ${StatusCode.Recibido}"]


Esperado = jsonSlurper.parseText(Esperado)

if(Obtenido.equals('Error de firma de mensaje. "ERROR ERROR DE FIRMA: "')) {
	Obtenido = 'Error de firma de mensaje. \"ERROR ERROR DE FIRMA: \"'
} else {
	Obtenido = jsonSlurper.parseText(Obtenido)
	
	if(Obtenido.data != null && Obtenido.data != []) {
		Obtenido.data[0].receive = jsonSlurper.parseText(Obtenido.data[0].receive)
		Obtenido.data[0].send = jsonSlurper.parseText(Obtenido.data[0].send)
	}
}
def evidencia = ["Endpoint":Endpoint,"Headers":Headers,"Enviado":Enviado,"Obtenido":Obtenido,"Esperado":Esperado,"StatusCode":StatusCode]

errores = coelsa.Util.validar(Obtenido, Esperado)

println errores
coelsa.Log.writeAccion(evidencia)

if(errores.size() > 0) {
	KeywordUtil.markFailed("Se encontraron diferencias entre lo esperado y lo recibido")
} else {
	coelsa.Log.changeStatusCase("Passed")
}
