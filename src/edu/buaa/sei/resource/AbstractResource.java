package edu.buaa.sei.resource;

public class AbstractResource {
	// resource instances number.
		private int capacity;
		
		// name of this resource, many resource may have the same name
		// to show the type.
		private String name = "";
		
		// id of this resource, unique.
		private String id = "";
		
		
		public AbstractResource(String name, String id, int capacity) {
			this.name = name;
			this.id = id;
			this.capacity = capacity;
		}
		
		public int getCapacity() {
			return capacity;
		}
		
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof AbstractResource)
				return id == ((AbstractResource) obj).id;
			return false;
		}
}
