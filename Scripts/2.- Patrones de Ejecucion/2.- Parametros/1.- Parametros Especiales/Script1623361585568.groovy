import internal.GlobalVariable as GlobalVariable

if(Parametros.id.equals('debin.id')) {
	Parametros.id = GlobalVariable.Debin.id
}

if(Parametros.objeto != null) {
	if(Parametros.objeto.ori_trx_id != null) {
		if(Parametros.objeto.ori_trx_id.toLowerCase().equals("nuevo")) {
			GlobalVariable.Debin.debin_trx++
			Parametros.objeto.ori_trx_id = GlobalVariable.Debin.debin_trx
		}
	}
}

if(Parametros.ori_trx_id != null)
	Parametros.ori_trx_id = GlobalVariable.Debin.debin_trx

return Parametros
