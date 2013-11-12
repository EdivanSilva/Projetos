package dominio;

public class Disciplina {

	private String codigo = "";

	private String nome = "";

	/**
	 * Atributo identificador do objeto.
	 */
	protected Integer id;

	/**
	 * M�todo para obter o n�mero identificador do objeto.
	 * 
	 * @return o n�mero identificador do objeto.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * M�todo para configurar o n�mero identificador do objeto.
	 * 
	 * @param id
	 *            � o n�mero identificador a ser usado.
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
