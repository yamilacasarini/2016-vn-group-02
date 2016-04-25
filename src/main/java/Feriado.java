import java.time.LocalDate;
import java.time.LocalTime;


public class Feriado {
	
	private int mes;
	private int dia;
	private IntervaloHorario intervaloFeriado;


public Feriado(int unMes, int unDia, IntervaloHorario unIntervalo)
{
	this.mes = unMes;
	this.dia = unDia;
	this.intervaloFeriado = unIntervalo;
}
	
public boolean comparateConDiaYMes(LocalDate unaFecha)
{
	return ((dia == unaFecha.getDayOfMonth())&& (mes == unaFecha.getMonthValue()));
}

public boolean incluisHorario(LocalTime horario)
{
	return(this.intervaloFeriado.incluyeHora(horario));
}



}
