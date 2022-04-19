import internal.GlobalVariable as GlobalVariable

if (Body.id != null) {
    Body.id = GlobalVariable.Debin.id
}

if (Body.operacion_original != null) {
    Body.operacion_original.id = GlobalVariable.Debin.id
}

if (Body.operacionOriginal != null) {
	Body.operacionOriginal.id = GlobalVariable.Debin.id
}

if (Body.operacion != null) {
	if(Body.operacion.id != null) {
		Body.operacion.id = GlobalVariable.Debin.id
	}
}

return Body

