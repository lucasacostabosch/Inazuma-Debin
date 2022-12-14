import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

String id_completo = 'Test Auto '

operacion_original = Body.operacion_original

if (operacion_original != null) {
	if(GlobalVariable.Debin.debin_qr_trx != null) {
		id_completo += GlobalVariable.Debin.debin_qr_trx
	}else if (GlobalVariable.Debin.InterOperable != null) {
		id_completo = GlobalVariable.Debin.InterOperable
	}
	Body.operacion_original.qr_id_trx = id_completo
}

if (Body.operacion != null) {
	if (Body.operacion.detalle != null) {
		if(Body.operacion.detalle.qr_id_trx != null) {
			def debin_qr_trx = GlobalVariable.Debin.debin_qr_trx

			def nxt = GlobalVariable.Debin.nxt_debin_qr_trx

			if ((nxt != null) && (debin_qr_trx == null)) {
				nxt++

				GlobalVariable.Debin.debin_qr_trx = nxt

				debin_qr_trx = nxt
			}
	
			def qr_id_trx = Body.operacion.detalle.qr_id_trx

			if (debin_qr_trx != null) {
				id_completo += debin_qr_trx
			} else if (qr_id_trx != null) {
				String Where = 'dac_credito_bancocod = \'' + GlobalVariable.ejecucion.Bank + '\''
				Where 	+= ' and DAC_QR_ID_TRX like \'Test Auto%\' and dac_tipo = \'DEBINQR\' and '
				Where	+= 'dac_debito_bancocod = \''  + GlobalVariable.ejecucion.Bank + '\''
				qr_id_trx = WebUI.callTestCase(findTestCase('3.- Puntos de Control/3.- Funciones/1.- Trx ID'),
					[	('SELECT') : 'TOP 1 DAC_ID',
						('WHERE') :  '',
						('ORDER_BY') : 'DAC_ID DESC',
						('SQL_var_name') : 'DAC_ID'],
					FailureHandling.STOP_ON_FAILURE)

				GlobalVariable.Debin.debin_qr_trx = qr_id_trx

				id_completo += qr_id_trx
			}
	
			Body.operacion.detalle.qr_id_trx = id_completo
		}
	}
}

return Body
