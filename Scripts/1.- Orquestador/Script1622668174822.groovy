import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil

Registros = coelsa.Orquestador.procesar(Acciones, Parametros, Respuestas)

coelsa.Log.newTestCase(Acciones, ObjetivoPrueba, IDCasoDePrueba)

try {
	for(int paso = 0; Registros.size() > paso; paso++) {
		coelsa.Log.setStep(paso)
		Registros[paso].Accion = Registros[paso].Accion.toLowerCase()

		coelsa.Orquestador.ejecutar(Registros[paso])
	}
} catch (Exception e) {
	if(e.message.equals("Verification failed")) {
		coelsa.Log.changeStatusCase("Failed")
	} else {
		coelsa.Log.changeStatusCase("Error")
		KeywordUtil.markError(e.message)
	}
} finally {
	coelsa.Log.saveEjecution()
	GlobalVariable.Debin = [:]
}
