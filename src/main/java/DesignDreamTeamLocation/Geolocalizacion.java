package DesignDreamTeamLocation;
import javax.persistence.*;

@Entity

public class Geolocalizacion {
	
	@Id
	@GeneratedValue
	@Column(name="idGeo")
	private int GeolocalizacionId;
	
	private double latitud;
	private double longitud;
	
	@Embedded
	private Domicilio domicilio = new Domicilio();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Localidad localidad = new Localidad();
	

	public Geolocalizacion()
	{
		
	}
	public Geolocalizacion(double latitud, double longitud, Domicilio domicilio, Localidad localidad) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.domicilio = domicilio;
		this.localidad = localidad;
	}

	public void setDomicilio(Domicilio domic) {
		domicilio = domic;

	}

	public void setLocalidad(Localidad loc) {
		localidad = loc;
	}

	public double distanciaCon(Geolocalizacion point) {
		double earthRadius = 6371000; // radio expresado en metros
		double lat2 = point.getLatitud();
		double lng2 = point.getLongitud();

		// formula de Haversine
		double dLat = Math.toRadians(lat2 - latitud);
		double dLng = Math.toRadians(lng2 - longitud);
		double a = Math.pow(Math.sin(dLat / 2), 2)
				+ Math.cos(Math.toRadians(latitud)) * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(dLng / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (double) (earthRadius * c);

		// retorna distancia en metros
		return dist;
	}

	public double getLatitud() {
		return this.latitud;
	}

	public double getLongitud() {
		return this.longitud;
	}

	public Domicilio getDomicilio() {
		return this.domicilio;
	}
	public Localidad getLocalidad()
	{
		return this.localidad;
	}
	public void setLatitud(double latitud)
	{
		this.latitud = latitud;
	}
	public void setLongitud(double longitud)
	{
		this.longitud =longitud;
	}

}
