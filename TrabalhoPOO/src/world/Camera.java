package world;
/*
 * Classe auxiliar para o correto posicionamento do que se é possivel ver na interface gráfica.
 */
public final class Camera {
	private static int x,y;
	public static int clamp(int atual,int min,int max){
		/*
		 * Usado na classe Player para fazer com que a camera siga o jogador,mas evitando que os limites do mapa
		 * seja ultrapassados;
		 */
		if(atual<min)
			atual=min;
		if(atual>max)
			atual=max;
		return atual;
	}
	public static int getX(){
		return x;
	}
	public static void setX(int x){
		Camera.x=x;
	}
	public static int getY(){
		return y;
	}
	public static void setY(int y){
		Camera.y=y;
	}
	private Camera(){
	}
}
