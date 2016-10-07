package aurora.util.resources;

public class RawModel {

	private int vaoID;
	private int vertexCount;

	/* Constructor Method */
	public RawModel(int vaoID, int vertexCount) {

		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	/* Gets the VAO ID */
	public int getVaoID() {
		
		return vaoID;
	}

	/* Gets the Vertex Count */
	public int getVertexCount() {
		
		return vertexCount;
	}
}