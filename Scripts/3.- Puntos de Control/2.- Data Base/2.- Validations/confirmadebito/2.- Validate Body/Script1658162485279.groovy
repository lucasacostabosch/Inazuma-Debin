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
def DebinId = GlobalVariable.Debin.id

Map Body = GlobalVariable.Body
Map respuesta1

if (response != null) {
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+DebinId+'\'', '')[0]
				
	if(select != null) {
										
		String cuitcomprador, cbvucomprador, moneda
		
		cuitcomprador = select.get('DAC_DEBITO_CUIT').toString()
		cbvucomprador = select.get('DAC_DEBITO_CBU').toString()
		
		Map datos_cuentas	
		datos_cuentas = [
			('cbu'):		cbvucomprador
			]
			
		Map comprador = [
			('cuit'):				cuitcomprador,
			('cuenta'): 			datos_cuentas
			]
				
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		} 
		
		String importe = select.get('DAC_IMPORTE')	//Importe traido desde la consulta a BD
		String[] s = importe.split("\\.")			//Split para tomar los decimales  
		String[] dec = s[1]							//Valores de los decimales del importe
		Integer a = s[1].length()					//Cantidad de caracteres en los decimales
		String decimal = ''							//Variable creada para inicializar los decimales 
			
		for(i = 0; i < a; i++) {					//Bucle para recorrer los decimales
			if(dec[i]!=0)							//Validacion si el digito decimal es diferente a 0
				decimal += dec[i] 					//Variable que concatena los digitos decamles dentro del bucle
		}
		
		if(decimal=='0000') {
			importe = s[0]
		}else {
			importe = s[0]+"."+decimal
		}
		
		Map detalle = [
			('ori_trx'):			select.get('DAC_ORI_TRX').toString(),		
			('ori_terminal'):		select.get('DAC_ORI_TERMINAL').toString(),
			('ori_adicional'):		select.get('DAC_ORI_ADICIONAL').toString(),
			('moneda'):				moneda,
			('importe'):			importe
			]
			
		def id = select.get('DAC_ID_HASH').toString()
		def codigo = '00'
					
		Map respuesta = [
			('codigo'):					codigo
			]
						
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP'),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO'),
			('plataforma'):			select.get('DAC_PLATAFORMA'),
			('imsi'):				select.get('DAC_IMSI'),
			('imei'):				select.get('DAC_IMEI'),
			('ubicacion'): [
					"lat":			select.get('DAC_LATITUD'),
					"lng":			select.get('DAC_LONGITUD'),
					"precision":	select.get('DAC_PRECISION')
				]
			]
			
		// Normalizar los datos del generador
		if(datosGenerador.ipCliente	== "")				datosGenerador.ipCliente = "string"
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = "string"
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = "string"
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = "string"
		if(datosGenerador.imei == "               ")	datosGenerador.imei = "string"
		
		Map operacion = [:]
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
						
		Map confirmadebito = [:]
		confirmadebito.operacion				= operacion
		confirmadebito.id						= id
		confirmadebito.respuesta				= respuesta
		confirmadebito.datosGenerador			= datosGenerador	
				
		//TODO
		// Campos que faltan definir
		
		String importeBody = Body.operacion.detalle.importe		//Valor del importe enviado desde el body
		String importeB
	
		if(importeBody.contains(".")) {
			String[] e = importeBody.split("\\.")				//Split para tomar el valor de los decimales del importe	
			String[] r = e[1]									//Array de los digitos de los decimales
			String f = e[1]										//Valor de los decimales del importe
			Integer y = e[1].length()							//Cantidad de digitos de decimales
			Integer dif											//Variable inicializada para la diferencia de los digitos enviados contra los esperados 
			String t = ''										//Variable inicializada para el valor final de los decimales despues de recorrerlos
			String z = ''										
			
			if(y<4) {											//Se valida si la cantidad de decimales es inferior es a 4 (la cantidad esperada)	
				dif = 4-y										//Resta entre los digitos esperados y los enviados desde el body. Esto servirá para determinar cuantos se necesitarán para cumplir con lo necesario
				for (i = dif; i < 4; i++) {					//Bucle para cargar los digitos necesarios
					z += 0										//Se irán concatenado 0, según las veces que se pase por el bucle
				}
				t = f+z											//Variable donde se concatena los digitos enviados, con los 0 necesarios para completar los 4 digitos
			}else {												//Si los digitos decimales enviados son los necesarios 4
				for(i = 0; i < a; i++) {						//Bucle para cargar cada digito de los decimales					
					if(r[i]!=0)
						t += r[i]
				}
			}
			importeB = e[0]+'.'+t			
		}else {
			importeB = importeBody
		}
		
		Body.operacion.detalle.importe = importeB
		Body.respuesta.remove('codigo')

		errores = coelsa.Util.validar(confirmadebito, Body)				
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$DebinId\'",
					selectbody:	select
					],
				errores:	errores
			]	
	}else {

		errores = 'Request: Consulta sin resultados. '
		respuesta1 = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$DebinId\'",
							selectbody:	select
							],
						errores: errores
					]
	}
		
}else {
	
	errores = 'Respuesta vacia. '
	db = ''
	respuesta1 = [
					db:db,
					errores: errores
				]
}

return respuesta1
