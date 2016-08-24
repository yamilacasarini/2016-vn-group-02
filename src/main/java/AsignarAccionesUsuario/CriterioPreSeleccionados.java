package AsignarAccionesUsuario;
import java.util.List;

import Repositorios.Usuario;

public class CriterioPreSeleccionados implements Criterio {
	List<Usuario> usuariosSeleccionados;

	public CriterioPreSeleccionados(List<Usuario> preSeleccionados) {
		super();
		this.usuariosSeleccionados = preSeleccionados;
	}

	public boolean esCumplidoPor(Usuario unUsuario) {
		return usuariosSeleccionados.stream().anyMatch(seleccionado -> seleccionado.equals(unUsuario));
	}

}
