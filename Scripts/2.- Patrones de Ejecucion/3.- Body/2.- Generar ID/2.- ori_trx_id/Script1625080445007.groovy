import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

if (Body.objeto?.ori_trx_id != null) {
	debin_trx = GlobalVariable.Debin.debin_trx

	ori_trx_id = Body.objeto.ori_trx_id

	if (debin_trx != null) {
		Body.objeto.ori_trx_id = debin_trx
	} else if (ori_trx_id != null) {
		String tipo = ""

		if(Body.objeto?.tipo != null) tipo = Body.objeto.tipo
		if(Body.operacion_original?.tipo != null) tipo = Body.operacion_original.tipo
		
		ori_trx_id = WebUI.callTestCase(findTestCase('3.- Puntos de Control/3.- Funciones/1.- Trx ID'),
		[ ('SELECT') : 'TOP 1 DAC_ORI_TRX_ID',
		('WHERE') : 'dac_tipo = \''+tipo.toUpperCase()+'\'',
		('ORDER_BY') : 'DAC_ORI_TRX_ID DESC',
		('SQL_var_name') : 'DAC_ORI_TRX_ID'],
		FailureHandling.STOP_ON_FAILURE)
		
		Body.objeto.ori_trx_id = ori_trx_id
	}
	
	GlobalVariable.Debin.debin_trx = ori_trx_id
}

return Body
