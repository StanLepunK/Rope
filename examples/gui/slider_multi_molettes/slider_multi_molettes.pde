/**
* GUI CROPE EXAMPLE
* Processing 3.5.4
* Rope Library 0.12.0.40
* 2016-2021
* v 0.2.0
* multi slider multi molette
*
*/
import rope.gui.slider.R_Slider;
import rope.vector.vec2;
import rope.R_State.State;


int x = 20 ;
int y = 20 ;
void setup() {
  size(400,200);
  State.papplet(this);
  multi_slider_setup(x,y);
}

void draw() {
	background(0);
	State.pointer(mouseX,mouseY);
  multi_slider_draw();
}


/**
SLIDER MULTI
*/
R_Slider multi_slider ;
void multi_slider_setup(int x, int y) {
	multi_slider = new R_Slider(new vec2(x,y), new vec2(200,20));
	multi_slider.set_molette_num(3);
	multi_slider.set_size_molette(5,40);
  multi_slider.set_rounded(20);
}

void multi_slider_draw() {
	multi_slider.update();
	multi_slider.show_structure();
	multi_slider.show_molette();
	
}


/**
Processing and Rope event
*/
void mouseWheel(MouseEvent e) {
	multi_slider.scroll(e);
}
