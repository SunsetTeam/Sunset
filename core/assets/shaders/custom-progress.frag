varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 u_uv;
uniform vec2 u_uv2;

uniform sampler2D u_texture_mask;
uniform int u_mask_progress;
uniform vec2 u_uv_mask;
uniform vec2 u_uv2_mask;

uniform float u_progress;
void main(){
    vec2 realCoors = (v_texCoords - u_uv) / (u_uv2-u_uv);
    vec2 maskTex=realCoors*(u_uv2_mask-u_uv_mask) +u_uv_mask;

    vec4 color1 = texture2D(u_texture, v_texCoords);
    vec4 color2 = texture2D(u_texture, maskTex);

    vec2 progresses = color2.rg * 255.0 / u_mask_progress;
    if (progresses.g>u_progress){
        color1.a = 0.0;
    } else if (progresses.g <= u_progress &&u_progress<= progresses.r){
        float progress= (u_progress-progresses.g)/(progresses.r-progresses.g);
        //        color1.a *= progress;
        vec4 originalColor=color1;
        //        color1=vec4(v_color*(1.0-progress)+color1*progress);
        color1=v_color;
        color1.a=progress*originalColor.a;

    }
    gl_FragColor=color1;
    //    gl_FragColor=color2;

}