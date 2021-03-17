package rope.costume;

import processing.core.PApplet;
import processing.core.PConstants;
import rope.vector.vec3;

public class R_House extends R_Shape {
	private int fill_roof = BLOOD;
	private int fill_wall = GRAY[6];
	private int fill_ground = BLACK;
	private int stroke_roof = BLACK;
	private int stroke_wall = BLACK;
	private int stroke_ground = BLACK;
	private float thickness = 1;
	private boolean aspect_is;

	private boolean roof_ar, roof_cr; // to draw or not the small roof side
	private vec3 offset = new vec3(-0.5f,0,0.5f); // to center the house; 

  private vec3 [] paa;
	private vec3 [] pcc;

	private int type = CENTER;
	
	public R_House(PApplet pa) {
		super(pa);
		build();
	}
  
  public R_House(PApplet pa, float size) {
  	super(pa);
  	size(size);
		build();
	}

	public R_House(PApplet pa, float sx, float sy, float sz) {
		super(pa);
		size(sx,sy,sz);
		build();
	}
	

	public void mode(int type) {
		this.type = type;
	}


	public void set_peak(float ra, float rc) {
		// check if this peak configuration is permitted
		if(ra + rc > 1.) {
			if(ra>rc) {
				ra = 1.0f-rc; 
			} else {
				rc = 1.0f-ra;
			}
		}

		if(ra > 0.) {
			roof_ar = true;
			if(paa != null && paa[0] != null) {
				paa[0].x = 1.0f-ra+offset.x;
			}
		}

		if(rc > 0.) {
			roof_cr = true;
			if(pcc != null && pcc[0] != null) {
				pcc[0].x = rc+offset.x;
			}
		}
	}
  // aspect
  // fill
  public void fill(int c) {
  	aspect_is = true;
  	fill_roof = fill_wall = fill_ground = c;
  }

	public void fill(float x, float y, float z, float a) {
		fill(color(x,y,z,a));
	}

  public void fill_roof(int c) {
		aspect_is = true;
		fill_roof = c;
	}

	public void fill_roof(float x, float y, float z, float a) {
		fill_roof(color(x,y,z,a));
	}

	public void fill_wall(int c) {
		aspect_is = true;
		fill_wall = c;
	}
  
	public void fill_wall(float x, float y, float z, float a) {
		fill_wall(color(x,y,z,a));
	}

  public void fill_ground(int c) {
		aspect_is = true;
		fill_ground = c;
	}

	public void fill_ground(float x, float y, float z, float a) {
		fill_ground(color(x,y,z,a));
	}

  // stroke
	public void stroke(int c) {
  	aspect_is = true;
  	stroke_roof = stroke_wall = stroke_ground = c;
  }
  
  public void stroke(float x, float y, float z, float a) {
		stroke(color(x,y,z,a));
	}

  public void stroke_roof(int c) {
		aspect_is = true;
		stroke_roof = c;
	}

	public void stroke_roof(float x, float y, float z, float a) {
		stroke_roof(color(x,y,z,a));
	}

  public void stroke_wall(int c) {
		aspect_is = true;
		stroke_wall = c;
	}
	public void stroke_wall(float x, float y, float z, float a) {
		stroke_wall(color(x,y,z,a));
	}

  public void stroke_ground(int c) {
		aspect_is = true;
		stroke_ground = c;
	}

	public void stroke_ground(float x, float y, float z, float a) {
    int c = this.pa.color(x,y,z,a);
		stroke_ground(c);
	}

	public void thickness(float thickness) {
		aspect_is = true;
		this.thickness = thickness;
	}

	public void aspect_is(boolean is) {
		this.aspect_is = is;
	}

  
  // build
	private void build() {
		paa = new vec3[5];
		pcc = new vec3[5];
		
		paa[0] = new vec3(1,-1,-0.5f); // roof peak
		paa[1] = new vec3(1,0,-1);
		paa[2] = new vec3(1,1,-1);
		paa[3] = new vec3(1,1,0);
		paa[4] = new vec3(1,0,0);

		pcc[0] = new vec3(0,-1,-0.5f); // roof peak
		pcc[1] = new vec3(0,0,-1);
		pcc[2] = new vec3(0,1,-1);
		pcc[3] = new vec3(0,1,0);
		pcc[4] = new vec3(0,0,0);

		for(int i = 0 ; i < paa.length ; i++) {
			paa[i].add(offset);
			pcc[i].add(offset);
		}
	}
  

	public void show() {
		float smallest_size = 0;
		for(int i = 0 ; i < 3 ; i++) {
			if(smallest_size == 0 || smallest_size > size.array()[i]) {
				smallest_size = size.array()[i];
			}
		}

    // DEFINE FINAL OFFSET
    vec3 def_pos = null;
	  if(this.type == PConstants.TOP) {
	  	if(pos == null) {
	  		def_pos = new vec3();
	  		def_pos.add(new vec3(0,size.y*0.5f,0));
	  	} else {
	  		def_pos = pos.copy();
	  		def_pos.add(new vec3(0,size.y*0.5f,0));		
	  	}
	  } else if(this.type == PConstants.BOTTOM) {
	  	if(pos == null) {
	  		def_pos = new vec3();
	  		def_pos.add(new vec3(0,-size.y,0));
	  	} else {
	  		def_pos = pos.copy();
	  		def_pos.add(new vec3(0,-size.y,0));		
	  	}
	  }



	  // WALL
	  if(aspect_is) {
	  	aspect(fill_wall,stroke_wall,thickness);
	  }
		// draw A : WALL > small and special side
		beginShape(other);
		if(def_pos == null) {
			if(!roof_ar) {
				vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			}
			for(int i = 1 ; i < paa.length ; i++) {
				vertex(paa[i].copy().mult(size),other);
			}
		} else {
			if(!roof_ar) {
				vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			}
			for(int i = 1 ; i < paa.length ; i++) {
				vertex(paa[i].copy().mult(size).add(def_pos),other);
			}
		}
		endShape(CLOSE, other);


	  // draw B : WALL > main wall
	  beginShape(other);
		if(def_pos == null) {
			vertex(paa[2].copy().mult(size),other);
			vertex(paa[1].copy().mult(size),other);
			vertex(pcc[1].copy().mult(size),other);
			vertex(pcc[2].copy().mult(size),other);
		} else {
			vertex(paa[2].copy().mult(size).add(def_pos),other);
			vertex(paa[1].copy().mult(size).add(def_pos),other);
			vertex(pcc[1].copy().mult(size).add(def_pos),other);
			vertex(pcc[2].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE, other);

	  // draw C : WALL > small and special side
		beginShape(other);
		if(def_pos == null) {
			if(!roof_cr) {
				vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pcc.length ; i++) {
				vertex(pcc[i].copy().mult(size),other);
			}
		} else {
			if(!roof_cr) {
				vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pcc.length ; i++) {
				vertex(pcc[i].copy().mult(size).add(def_pos),other);
			}	
		}
		endShape(CLOSE,other);

		// draw D : WALL > main wall
		beginShape(other);
		if(def_pos == null) {
			vertex(paa[3].copy().mult(size),other);
			vertex(paa[4].copy().mult(size),other);
			vertex(pcc[4].copy().mult(size),other);
			vertex(pcc[3].copy().mult(size),other);
		} else {
			vertex(paa[3].copy().mult(size).add(def_pos),other);
			vertex(paa[4].copy().mult(size).add(def_pos),other);
			vertex(pcc[4].copy().mult(size).add(def_pos),other);
			vertex(pcc[3].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);





    // GROUND
    if(aspect_is) {
	  	aspect(fill_ground,stroke_ground,thickness);
	  }
		// draw G : GROUND
		beginShape(other);
		if(def_pos == null) {
			vertex(paa[2].copy().mult(size),other);
			vertex(pcc[2].copy().mult(size),other);
			vertex(pcc[3].copy().mult(size),other);
			vertex(paa[3].copy().mult(size),other);
		} else {
			vertex(paa[2].copy().mult(size).add(def_pos),other);
			vertex(pcc[2].copy().mult(size).add(def_pos),other);
			vertex(pcc[3].copy().mult(size).add(def_pos),other);
			vertex(paa[3].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);



    // ROOF
    if(aspect_is) {
	  	aspect(fill_roof,stroke_roof,thickness);
	  }
    // draw E : ROOF > main roof
		beginShape(other);
		if(def_pos == null) {
			vertex(paa[4].copy().mult(size),other);
			vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(pcc[4].copy().mult(size),other);			
		} else {
			vertex(paa[4].copy().mult(size).add(def_pos),other);
			vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(pcc[4].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);

		// draw F : ROOF > main roof
		beginShape(other);
		if(def_pos == null) {
			vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(paa[1].copy().mult(size),other);
			vertex(pcc[1].copy().mult(size),other);
			vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
		} else {
			vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(paa[1].copy().mult(size).add(def_pos),other);
			vertex(pcc[1].copy().mult(size).add(def_pos),other);
			vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
		}
		endShape(CLOSE,other);

		// DRAW AR  > small side roof
		if(roof_ar) {
			beginShape(other);
			if(def_pos == null) {
				vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
				vertex(paa[1].copy().mult(size),other);
				vertex(paa[4].copy().mult(size),other);
			} else {
				vertex(paa[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
				vertex(paa[1].copy().mult(size).add(def_pos),other);
				vertex(paa[4].copy().mult(size).add(def_pos),other);
			}
			endShape(CLOSE,other);
		}

		// DRAW CR > small side roof
		if(roof_cr) {
			beginShape(other);
			if(def_pos == null) {
				vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
				vertex(pcc[1].copy().mult(size),other);
				vertex(pcc[4].copy().mult(size),other);
			} else {
				vertex(pcc[0].copy().mult(new vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
				vertex(pcc[1].copy().mult(size).add(def_pos),other);
				vertex(pcc[4].copy().mult(size).add(def_pos),other);
			}
			endShape(CLOSE,other);
		}
	}
}