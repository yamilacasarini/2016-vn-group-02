package MainPackage;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

	public static void main(String[] args) {
		Integer puerto = 10024;
		port(puerto);

		System.out.println("Iniciando servidor");

		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		MainController home = new MainController();

		staticFileLocation("/templates");
		get("/", home::mostrar, engine);

		get("/administrador", home::mostrarAdmin, engine);
		get("/usuario", home::mostrarUser, engine);

		get("/admin_terminales", home::mostrarTerminales, engine);
		get("/buscar_terminales", home::buscarTerminal, engine);
		get("/editar_terminal", home::mostrarEditarTerminal, engine);
		get("/admin_acciones", home::mostrarAdminAcciones, engine);
		delete("/borrar_terminal", home::borrarTerminal);
		
		get("/admin_pois", home::mostrarPois, engine);
		get("/buscar_pois", home::buscarPoisAdmin, engine);
		get("/editar_poi", home::editarPoi, engine);
		delete("/borrar_poi", home::borrarPoi);
		get("/nuevo_poi", home::agregarPoi, engine);
		get("/masDetallePoi",home::masDetalle,engine);
		// SI ES UNA MIERDA... PERO AL PARECER SPARK NO ACEPTA POST
		get("/agregar_poi",home::nuevoPoi,engine);
		
		get("/usuarioBusqueda", home::buscarPois, engine);
		get("/ver_mas", home::verMas, engine);

		get("/admin_consultas", home::mostrarConsultas, engine);
		get("/buscar_consultas", home::buscarBusquedas, engine);
		get("/ver_pois_consultas", home::verPoisConsultas, engine);

		get("*", (request, response) -> {
			return new ModelAndView(null, "notFound.hbs");
		}, engine);
		Runtime rt = Runtime.getRuntime();

		/*try {
			rt.exec("\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\" http:\\\\localhost:"
					+ puerto.toString());
		} catch (IOException e) {
		}*/
	}

}