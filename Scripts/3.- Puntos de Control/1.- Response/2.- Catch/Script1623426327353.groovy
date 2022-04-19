import internal.GlobalVariable as GlobalVariable

'Persistimos el ID_Hash para utlizar en el resto de los métodos.'
if(Response.debin != null){
	if(Response.debin.id != null) {
		GlobalVariable.Debin.id = Response.debin.id
	}
}
 
if(Response.objeto != null){
	if(Response.objeto.id != null) {
		GlobalVariable.Debin.id = Response.objeto.id
	}
}
