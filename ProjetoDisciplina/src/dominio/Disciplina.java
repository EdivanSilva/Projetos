package dominio;

public class Disciplina {

	private String codigo = "";

	private String nome = "";

	/**
	 * Atributo identificador do objeto.
	 */
	protected Integer id;

	/**
	 * Método para obter o número identificador do objeto.
	 * 
	 * @return o número identificador do objeto.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Método para configurar o número identificador do objeto.
	 * 
	 * @param id
	 *            é o número identificador a ser usado.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
