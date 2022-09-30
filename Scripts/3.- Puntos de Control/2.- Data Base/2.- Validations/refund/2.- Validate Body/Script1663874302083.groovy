import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar

def jsonSlurper = new JsonSlurper()

Map Body = GlobalVariable.Body
Map respuesta

if (response != null) {
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id+'\'', '')[0]
	
	println select
			
	/*if(select != null) {
		
		String authorization_code, transaction_reference_id, trace_id, type
		
		authorization_code			=	select.get('')
		transaction_reference_id	=	select.get('')
		trace_id					=	select.get('')
		type						=	select.get('')
		
		def value_amount_oc			=	select.get('')
		
		String currency_amount_ot
		
		currency	=	select.get('')
		
		Map amount_ot	=	[
			('value'):		value_amount_oc,
			('currency'):	currency_amount_ot
			]
		
		Map original_transaction	=	[
			('authorization_code'):			authorization_code,
			('transaction_reference_id'):	transaction_reference_id,
			('trace_id'):					trace_id,
			('type'):						type,
			('amount'):						amount_ot
			]	
		
		String authorization_code
		
		authorization_code	=	select.get('')
		
		def value_amount	=	select.get('')
		
		String currency_amount
		
		currency_amount		=	select.get('')
		
		Map amount	=	[
			('value'):		value_amount,
			('currency'):	currency_amount
			]
			
		String name, identification_number
		
		name 					=	select.get('')
		identification_number	=	select.get('')

		String type, number
		
		type	=	select.get('')
		number	=	select.get('')
		
		Map document_payer	=	[
			('type'):	type,
			('number'):	number
			]
		
		String cbu, cvu
		
		cbu	=	select.get('')
		cvu	=	select.get('') 
		
		Map account_payer	=	[
			('cbu'):	cbu,
			('cvu'):	cvu
			]
		
		String name, cuit, bcra_id
		
		name	=	select.get('')
		cuit	=	select.get('')
		bcra_id	=	select.get('')
		
		Map wallet_payer	=	[
			('name'):		name,
			('cuit'):		cuit,
			('bcra_id'):	bcra_id
			]
		
		Map payer 	=	[
			('name'):					name,
			('identification_number'):	identification_number,
			('document'):				document_payer,
			('account'):				account_payer,
			('wallet'):					wallet_payer
			]
			
		String reverse_domain, cuit_acquirer
		
		reverse_domain	=	select.get('')
		cuit_acquirer	=	select.get('')
		
		Map acquirer	=	[
			('reverse_domain'):	reverse_domain,
			('cuit'):	cuit_acquirer
			]
				
		String soft_descriptor, cuit_merchant
		
		soft_descriptor	=	select.get('')
		cuit_merchant	=	select.get('')
		
		String cbu_account_merchant, cvu_account_merchant
		
		cbu_account_merchant	=	select.get('')
		cvu_account_merchant	=	select.get('')
		
		Map account_merchant	=	[
			('cbu'):	cbu_account_merchant,
			('cvu'):	cvu_account_merchant
			]
			
		Map merchant		=	[
			('soft_descriptor'):	soft_descriptor,
			('cuit'):				cuit_merchant,
			('account'):			account_merchant
			]
		
	
		Map refund = [:]
		
		refund.original_transaction	=	original_transaction
		refund.authorization_code	= 	authorization_code
		refund.amount				=	amount
		refund.payer				=	payer
		refund.acquirer				=	acquirer
		refund.merchant				=	merchant
		
		errores = coelsa.Util.validar(refund, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
					selectbody:	select
					],
				errores:	errores
			]
	}else {
		errores = 'Request: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
							selectbody:	select
							],
						errores: errores
					]
	}*/
		
	errores = ''
	respuesta = [
					db: [
						querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
						selectbody:	select
						],
					errores: errores
				]
	
}else {
	errores = 'Respuesta vacia. '
	db = ''
	respuesta = [
					db:db,
					errores: errores
				]
}

return respuesta
