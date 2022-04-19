import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

if (Body.objeto != null) {
	debin_trx = GlobalVariable.Debin.debin_trx

	ori_trx_id = Body.objeto.ori_trx_id

	if (debin_trx != null) {
		Body.objeto.ori_trx_id = debin_trx
	} else if (ori_trx_id != null) {
		def credito = Body.credito

		def cbanco = credito.banco
		
		String dac_credito = 'DAC_CREDITO_CBU'
		
		if(cbanco == '000'){
			dac_credito = 'DAC_CREDITO_CVU'
		}

		def credito_cuenta = credito.cuenta

		def credito_cbu = credito_cuenta.cbu

		def debito = Body.debito

		def debito_cuenta = debito.cuenta

		def dbanco = debito.banco
		
		String dac_debito = 'DAC_DEBITO_CBU'
		
		if(dbanco == '000'){
			dac_debito = 'DAC_DEBITO_CVU'
		}

		def debito_cbu = debito_cuenta.cbu
		
		ori_trx_id = WebUI.callTestCase(findTestCase('3.- Puntos de Control/2.- Data Base/1.- Trx ID'),
			[('SELECT') : ['TOP 1 DAC_ORI_TRX_ID'],
				('WHERE') : [('connector') : 'dac_tipo = \''+Body.objeto.tipo.toUpperCase()+'\''],
				('ORDER_BY') : 'DAC_ORI_TRX_ID DESC',
				('SQL_var_name') : 'DAC_ORI_TRX_ID'],
			FailureHandling.STOP_ON_FAILURE)

		Body.objeto.ori_trx_id = ori_trx_id
	}
	
	GlobalVariable.Debin.debin_trx = ori_trx_id
}

return Body
