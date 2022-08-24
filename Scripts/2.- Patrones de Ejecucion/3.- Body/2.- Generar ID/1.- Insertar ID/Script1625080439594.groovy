import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

if (Body.id != null) {
    Body.id = GlobalVariable.Debin.id
}

if (Body.operacion_original != null) {
	if(Body.operacion_original.id != null)
		Body.operacion_original.id = GlobalVariable.Debin.id
}

if (Body.operacionOriginal != null) {
	if(Body.operacionOriginal.id != null)
		Body.operacionOriginal.id = GlobalVariable.Debin.id
}

if (Body.operacion != null) { 
	if(Body.operacion.id != null) {
		Body.operacion.id = GlobalVariable.Debin.id
	}
}

if( GlobalVariable.Accion.equals("payment")
 || GlobalVariable.Accion.equals("paymentvalidation")
 || GlobalVariable.Accion.equals("refund")
 || GlobalVariable.Accion.equals("refundvalidation")
 ) {
	
	if(GlobalVariable.Debin.InterOperable == null) {
		Number inter = WebUI.callTestCase(findTestCase('3.- Puntos de Control/3.- Funciones/1.- Trx ID'),
				[	('SELECT') : 'TOP 1 DAC_ID',
				 	('WHERE') :  '',
				 	('ORDER_BY') : 'DAC_ID DESC',
				 	('SQL_var_name') : 'DAC_ID'],
				FailureHandling.STOP_ON_FAILURE)
				
		GlobalVariable.Debin.InterOperable = inter
	}
	Body.qr_id = GlobalVariable.Debin.InterOperable
}

return Body
