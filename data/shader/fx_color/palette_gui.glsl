/**
* PALETTE GUI
* 2021-2021
* v 0.1.0
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope
*/

#ifdef GL_ES
precision mediump float;
#endif

uniform float value;
uniform vec2 resolution;
uniform int mode;

// GRADIENT PART
uniform float min_hue = 0.0;
uniform float max_hue = 1.0;

uniform float start_sat = 0.5;
uniform float min_sat = 0.0;
uniform float max_sat = 1.0;

uniform float start_bri = 0.5;
uniform float min_bri = 0.0;
uniform float max_bri = 1.0;
uniform bool vert_is = false;

/**
* @see https://thebookofshaders.com/05/?lan=fr
* Function from Iñigo Quiles
* @see https://www.shadertoy.com/view/MsS3Wc
*/
vec3 hsb_to_rgb(vec3 c){
	vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0,4.0,2.0),
                             6.0)-3.0)-1.0,
                     0.0,
                     1.0 );
	rgb = rgb*rgb*(3.0-2.0*rgb);
	return c.z * mix( vec3(1.0), rgb, c.y);
}

float map(float value, float min_0, float max_0, float min_1, float max_1) {
  return min_1 + (max_1 - min_1) * (value - min_0) / (max_0 - min_0);
}

void main(void) {
	vec2 uv = gl_FragCoord.xy/resolution.xy;
	vec3 color = vec3(value,1.0,1.0);
	color = hsb_to_rgb(color);
  float dir = uv.x;
  if(vert_is) {
    dir = uv.y;
  }

	// HSB
	if(mode == 0) color = hsb_to_rgb(vec3(value,uv.x,uv.y)); // from white with hue change
	// RGB
	else if(mode == 3) color = vec3(value,uv.x,uv.y); // from white with red
	else if(mode == 4) color = vec3(uv.x, value,uv.y); // from white with green
	else if(mode == 5) color = vec3(uv.x, uv.y, value); // from white with blue
	// GLOBAL HSB
	else if(mode == 10) color = hsb_to_rgb(vec3(uv.x,1.0,1.0)); // normal
	else if(mode == 11) color = hsb_to_rgb(vec3(uv.x,1.0,uv.y)); // from black
	else if(mode == 12) color = hsb_to_rgb(vec3(uv.x,uv.y,1.0)); // from white
  // GRADIENT
	else if(mode == 13) {
		float sat = 1.0;
		float bri = 1.0;
		if(uv.y < 0.5) {
			bri = map(uv.y, 0, 0.5, 0, 1.0);
		} else {
			sat = map(uv.y, 0.5, 1.0, 1.0, 0);
		}
		color = hsb_to_rgb(vec3(uv.x,sat,bri)); // from white to color to black
	// GRADIENT SETABLE
	} else if(mode == 19) {
    color = hsb_to_rgb(vec3(dir,1- start_sat,start_bri)); // normal
  } else if(mode == 20) {
    
		float sat = 1.0;
		float bri = 1.0;
		if(dir < start_bri) bri = map(dir, 
																	0, start_bri,
																	min_bri, max_bri);		
		if(dir > start_sat) sat = map(dir,
																	start_sat, 1.0, 
																	max_sat, min_sat);


		color = hsb_to_rgb(vec3(value,sat,bri)); // from white to color to black

	// DEFAULT
	} else {
		color = hsb_to_rgb(vec3(value,uv.x,uv.y)); // from white with hue change
	}
	gl_FragColor = vec4(color,1.0);
}