package MainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import Controllers.ControllerRepoBusquedas;
import Controllers.ControllerRepoPoi;
import Controllers.ControllerRepoTerminales;
import Repositorios.Buscador;
import Repositorios.Busqueda;
import Repositorios.RepoPOIs;
import Repositorios.RepoTerminales;
import Repositorios.Terminal;
import TypePois.POI;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MainController implements WithGlobalEntityManager, TransactionalOps {
	private String nombreUsuario;

	public Void borrarTerminal(Request request, Response response) {
		System.out.println("Se quiso borrar terminal nombre: " + request.queryParams("nombre"));
		withTransaction(() -> {
			Terminal terminalABorrar = RepoTerminales.getInstance().buscameUnaTerminal(request.queryParams("nombre"));
			System.out.println("Terminal encontrada");
			ControllerRepoTerminales.getInstance().eliminarUnaTerminal(terminalABorrar);
		});
		System.out.println("La puta que te pario");
		response.redirect("/admin_terminales");
		return null;
	}

	public ModelAndView mostrar(Request request, Response response) {
		System.out.println("Mostrar Main");
		return new ModelAndView(null, "home.hbs");
	}

	public ModelAndView agregarPoi(Request request, Response response) {
		System.out.println("Nuevo Poi");
		return new ModelAndView(null, "agregar_poi.hbs");
	}

	public Void borrarPoi(Request request, Response response) {
		System.out.println("Se quiso borrar un poi" + request.queryParams("id"));
			ControllerRepoPoi.getInstance().borrarUnPOIporId(request.queryParams("id"));
		
		return null;
	}

	public ModelAndView buscarTerminal(Request request, Response response) {
		System.out.println("Buscar Terminal");
		HashMap<String, Object> viewModel = new HashMap<>();

		List<Terminal> terminales = ControllerRepoTerminales.getInstance().listarTerminales("", -1);
		viewModel.put("terminales", terminales);
		return new ModelAndView(viewModel, "admin_terminales.hbs");
	}

	public ModelAndView mostrarEditarTerminal(Request request, Response response) {
		System.out.println("Editar Terminal");
		String nombreFiltro = request.queryParams("nombre");
		List<Terminal> terminales = ControllerRepoTerminales.getInstance().listarTerminales(nombreFiltro, -1);
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("terminales", terminales.get(0));
		return new ModelAndView(viewModel, "editar_terminal.hbs");
	}

	public ModelAndView mostrarAdminAcciones(Request request, Response response) {
		System.out.println("Administrar acciones por terminal");
		return new ModelAndView(null, "admin_acciones.hbs");
	}

	public ModelAndView mostrarEditarPoi(Request request, Response response) {
		System.out.println("Editar POI");
		return new ModelAndView(null, "editar_poi.hbs");
	}

	public ModelAndView mostrarAdmin(Request request, Response response) {
		System.out.println("Mostrar Panel Admin");
		return new ModelAndView(null, "Administrador.hbs");
	}

	public ModelAndView mostrarUser(Request request, Response response) {
		nombreUsuario = request.queryParams("nombreFiltro");

		System.out.println("Mostrar Usuario " + nombreUsuario);
		if (RepoTerminales.getInstance().buscameUnaTerminal(nombreUsuario).equals(null)) {
			String barrio = request.queryParams("rrioba");
			HashMap<String, Integer> comunas = ControllerRepoPoi.getInstance().cargarComunas();
			ControllerRepoTerminales.getInstance().agregarUnaTerminal(nombreUsuario, comunas.get(barrio));
		}

		return new ModelAndView(null, "usuario.hbs");
	}

	public ModelAndView mostrarTerminales(Request request, Response response) {
		System.out.println("Mostrar Admin Terminales");
		return new ModelAndView(null, "admin_terminales.hbs");
	}

	public ModelAndView mostrarConsultas(Request request, Response response) {
		System.out.println("Mostrar Consultas");
		return new ModelAndView(null, "admin_consultas.hbs");
	}

	public ModelAndView buscarBusquedas(Request request, Response response) {
		System.out.println("Buscando busquedas");
		List<Busqueda> busquedas = ControllerRepoBusquedas.getInstance().listarBusquedas(request.queryParams("nombre"),
				request.queryParams("desde"), request.queryParams("hasta"), request.queryParams("cantidad"));
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("consultas", busquedas);
		return new ModelAndView(null, "admin_pois.hbs");
	}

	public ModelAndView mostrarPois(Request request, Response response) {
		System.out.println("Ver pois sin listado");
		return new ModelAndView(null, "admin_pois.hbs");
	}

	public ModelAndView filtrarNombreTipoPois(Request request, Response response) {
		System.out.println("FiltrarNombrePois");

		HashMap<String, Object> viewModel = new HashMap<>();
		String nombreFiltro = request.queryParams("nombreFiltro");
		String tipoFiltro = request.queryParams("tipoFiltro");

		List<POI> pois = new Controllers.ControllerRepoPoi().listarPOIsParaAdmin(nombreFiltro, tipoFiltro);
		viewModel.put("listadoPOIs", pois);

		List<String> coordenadas = new ArrayList<String>();
		pois.forEach(unPoi -> coordenadas
				.add("{lat:" + unPoi.getPoint().getLatitud() + ", lng:" + unPoi.getPoint().getLongitud() + "}"));
		viewModel.put("latitudes", coordenadas);
		return new ModelAndView(viewModel, "admin_pois.hbs");
	}

	public ModelAndView busquedaUsuario(Request request, Response response) {
		nombreUsuario = request.queryParams("nombreFiltro");
		System.out.println("busquedaUsuario" + nombreUsuario);
		return new ModelAndView(null, "usuario.hbs");
	}

	public ModelAndView verMas(Request request, Response response) {
		System.out.println("Ver mas");
		HashMap<String, Object> viewModel = new HashMap<>();
		String nombreFiltro = request.queryParams("nombreFiltro");
		List<POI> pois = new Buscador().buscarPoisHibernate(nombreFiltro,
				RepoTerminales.getInstance().buscameUnaTerminal(nombreUsuario));
		viewModel.put("listadoPOIs", pois);
		return new ModelAndView(viewModel, "usuario.hbs");
	}

	public ModelAndView buscarPois(Request request, Response response) {
		System.out.println("busquedaUsuario");
		HashMap<String, Object> viewModel = new HashMap<>();
		String nombreFiltro = request.queryParams("nombreFiltro");
		List<POI> pois = new Buscador().buscarPoisMongo(nombreFiltro,
				RepoTerminales.getInstance().buscameUnaTerminal(nombreUsuario));
		viewModel.put("listadoPOIs", pois);
		return new ModelAndView(viewModel, "usuario.hbs");
	}

	public ModelAndView editarPoi(Request request, Response response) {
		System.out.println("busquedaUsuario");
		HashMap<String, Object> viewModel = new HashMap<>();
		String nombreFiltro = request.queryParams("id");
		POI pois = RepoPOIs.getInstance().obtenerDeHibernate(Integer.parseInt(nombreFiltro));
		viewModel.put("poi", pois);
		return new ModelAndView(viewModel, "editar_poi.hbs");
	}
}