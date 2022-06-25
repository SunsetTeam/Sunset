#define HIGHP
varying lowp vec4 v_color;
varying lowp vec4 v_mix_color;
varying highp vec2 v_texCoords;
uniform highp sampler2D u_texture;

uniform vec2 u_centerPosition;
uniform vec4 u_lightColor;
uniform float u_lightDistance;
uniform float u_lightRadius;

void main(){
    vec4 c = texture2D(u_texture, v_texCoords);
    vec4 originalColor=v_color * mix(c, vec4(v_mix_color.rgb, c.a), v_mix_color.a);
    float distance= clamp(
    abs(distance(gl_FragCoord.xy, u_centerPosition.xy)-u_lightRadius)
    , 0.0, u_lightDistance);

    //    float progress=smoothstep(u_lightDistance,0.0,distance);
    float progress=1.0-distance/u_lightDistance;
    gl_FragColor = mix(originalColor, u_lightColor, progress*progress);
}
