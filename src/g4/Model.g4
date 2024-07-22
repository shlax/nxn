grammar Model;

model returns [ org.nxn.model.ParsedModel result ] :
    { java.util.List<org.nxn.model.ParsedFace> lf = new java.util.ArrayList<>(); }
    p=points '{' f=face { lf.add($f.r); } ( fi=face { lf.add($fi.r); } )* '}'
    { $result = new org.nxn.model.ParsedModel( $p.r.toArray(new org.nxn.math.Vector3f[0] ), lf.toArray( new org.nxn.model.ParsedFace[0] ) ); };

face returns [ org.nxn.model.ParsedFace r ] :
    '[' a=vertex ',' b=vertex ',' c=vertex ']'
    { $r = new org.nxn.model.ParsedFace($a.r, $b.r, $c.r); };

vertex returns [ org.nxn.model.ParsedVertex r ] :
    i=intNum ':' n=vector3 ':' u=uvs
    { $r = new org.nxn.model.ParsedVertex($i.r, $n.r, $u.r.toArray( new org.nxn.math.Vector2f[0] ) ); };

uvs returns [ java.util.List<org.nxn.math.Vector2f> r ] :
    { $r = new java.util.ArrayList<>(); }
    '[' p=vector2 { $r.add($p.r); } (',' pi=vector2 { $r.add($pi.r); } )* ']' ;

points returns [ java.util.List<org.nxn.math.Vector3f> r ] :
    { $r = new java.util.ArrayList<>(); }
    '[' p=vector3 { $r.add($p.r); } (',' pi=vector3 { $r.add($pi.r); } )* ']' ;

vector3  returns [ org.nxn.math.Vector3f r ]:
    '(' a=floatNum ',' b=floatNum ',' c=floatNum ')'
    { $r = new org.nxn.math.Vector3f($a.r, $b.r, $c.r); };

vector2  returns [ org.nxn.math.Vector2f r ]:
    '(' a=floatNum ',' b=floatNum ')'
    { $r = new org.nxn.math.Vector2f($a.r, $b.r); };

floatNum returns [ float r ]:
    s=('+'|'-')? n=DIGITS ('.' m=DIGITS)? (('e'|'E') e=('+'|'-')? p=DIGITS )?
    {
        StringBuilder sb = new StringBuilder();
        if($s.text != null) sb.append($s.text);
        sb.append($n.text);
        if($m.text != null){
            sb.append('.').append($m.text);
        }
        if($p.text != null){
            sb.append('E');
            if($e.text != null) sb.append($e.text);
            sb.append($p.text);
        }
        $r = Float.parseFloat(sb.toString());
    };

intNum returns [ int r ]:
    s=('+'|'-')? n=DIGITS
    { $r = Integer.parseInt($s.text == null ? $n.text : $s.text + $n.text ); } ;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;
