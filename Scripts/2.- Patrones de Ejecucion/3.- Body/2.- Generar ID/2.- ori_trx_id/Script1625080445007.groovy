import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

if (Body.objeto?.ori_trx_id != null) {
	debin_trx = GlobalVariable.Debin.debin_trx

	if (debin_trx != null) {
		Body.objeto.ori_trx_id = debin_trx
	} else {
		String tipo = ""

		if(Body.objeto?.tipo != null) tipo = Body.objeto.tipo
		if(Body.operacion_original?.tipo != null) tipo = Body.operacion_original.tipo
		String where = 'dac_tipo = \''+tipo.toUpperCase()+'\''
		debin_trx = WebUI.callTestCase(findTestCase('3.- Puntos de Control/3.- Funciones/1.- Trx ID'),
		[ ('SELECT') : 'TOP 1 DAC_ORI_TRX_ID',
		('WHERE') : '',
		('ORDER_BY') : 'DAC_ORI_TRX_ID DESC',
		('SQL_var_name') : 'DAC_ORI_TRX_ID'],
		FailureHandling.STOP_ON_FAILURE)
		
		Body.objeto.ori_trx_id = debin_trx
	}
	
	GlobalVariable.Debin.debin_trx = debin_trx
}

return Body
