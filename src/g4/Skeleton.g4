grammar Skeleton;

skeleton returns [ org.nxn.model.skeleton.ParsedJoint result ]:
    '[' j=joint ']' { $result = $j.r; } ;

joint returns [ org.nxn.model.skeleton.ParsedJoint r ]:
    nm=NAME ':' ( (v=vector3 o=axis) | a=angles ) (':' b=bindings  )? ( ':' l=jointList )?
    { $r = new org.nxn.model.skeleton.ParsedJoint( $nm.text, $v.r, $o.r, $a.r, $b.r, $l.r); };

axis returns [ org.nxn.math.Axis[] r ]:
    ':' t=NAME
    { $r = $t.text.chars().mapToObj(c -> org.nxn.math.Axis.valueOf(String.valueOf((char)c))).toArray(l -> new org.nxn.math.Axis[l]); };

angles returns [ org.nxn.model.skeleton.ParsedAngle[] r ]:
    { java.util.List<org.nxn.model.skeleton.ParsedAngle> l = new java.util.ArrayList<org.nxn.model.skeleton.ParsedAngle>(); }
    '(' j=angle { l.add($j.r); } (',' k=angle { l.add($k.r); } )* ')'
    { $r = l.toArray(new org.nxn.model.skeleton.ParsedAngle[0]); };

angle returns [ org.nxn.model.skeleton.ParsedAngle r ]:
    n=floatNum ':' f=NAME '->' t=NAME
    { $r = new org.nxn.model.skeleton.ParsedAngle(org.nxn.math.Axis.valueOf($f.text), org.nxn.math.Axis.valueOf($t.text), $n.r); };

jointList returns [ org.nxn.model.skeleton.ParsedJoint[] r ]:
    { java.util.List<org.nxn.model.skeleton.ParsedJoint> l = new java.util.ArrayList<org.nxn.model.skeleton.ParsedJoint>(); }
    '[' j=joint { l.add($j.r); } (',' k=joint { l.add($k.r); } )* ']'
    { $r = l.toArray(new org.nxn.model.skeleton.ParsedJoint[0]); } ;

bindings returns [ org.nxn.model.skeleton.ParsedBinding[] r ]:
    { java.util.List<org.nxn.model.skeleton.ParsedBinding> l = new java.util.ArrayList<org.nxn.model.skeleton.ParsedBinding>(); }
    '[' n=binding { l.add($n.r); } ( ',' m=binding { l.add($m.r); } )* ']'
    { $r = l.toArray( new org.nxn.model.skeleton.ParsedBinding[0] ); } ;

binding returns [ org.nxn.model.skeleton.ParsedBinding r ]:
    n=NAME ':' i=indList { $r = new org.nxn.model.skeleton.ParsedBinding($n.text, $i.r); } ;

indList returns [ int[] r ]:
    { java.util.ArrayList<Integer> l = new java.util.ArrayList<Integer>(); }
    '[' n=indNum { l.add($n.r); } ( ',' m=indNum { l.add($m.r); } )* ']'
    { $r = l.stream().mapToInt(Integer::intValue).toArray(); };

vector3  returns [ org.nxn.math.Vector3f r ]:
    '(' a=floatNum ',' b=floatNum ',' c=floatNum ')'
    { $r = new org.nxn.math.Vector3f($a.r, $b.r, $c.r); };

floatNum returns [ float r ]:
    s=('+'|'-')? n=DIGITS ('.' m=DIGITS)? (('e'|'E') e=('+'|'-')? p=DIGITS )? {
        StringBuilder sb = new StringBuilder();
        if($s.text != null){
            sb.append($s.text);
        }
        sb.append($n.text);
        if($m.text != null){
            sb.append('.').append($m.text);
        }
        if($p.text != null){
            sb.append('E');
            if($e.text != null){
                sb.append($e.text);
            }
            sb.append($p.text);
        }
        $r = Float.parseFloat(sb.toString());
    };

indNum returns [ int r ]:
    n=DIGITS
    { $r = Integer.parseInt($n.text); } ;

NAME : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;
