// Generated from /home/pochodnicky/wrksp/no-git/NXN/src/g4/Skeleton.g4 by ANTLR 4.13.1
package org.nxn.model.skeleton.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SkeletonParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, NAME=12, DIGITS=13, WS=14;
	public static final int
		RULE_skeleton = 0, RULE_joint = 1, RULE_jointList = 2, RULE_bindings = 3, 
		RULE_binding = 4, RULE_indList = 5, RULE_vector3 = 6, RULE_floatNum = 7, 
		RULE_indNum = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"skeleton", "joint", "jointList", "bindings", "binding", "indList", "vector3", 
			"floatNum", "indNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'['", "']'", "':'", "','", "'('", "')'", "'+'", "'-'", "'.'", 
			"'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"NAME", "DIGITS", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Skeleton.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SkeletonParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SkeletonContext extends ParserRuleContext {
		public org.nxn.model.skeleton.ParsedJoint r;
		public JointContext j;
		public JointContext joint() {
			return getRuleContext(JointContext.class,0);
		}
		public SkeletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skeleton; }
	}

	public final SkeletonContext skeleton() throws RecognitionException {
		SkeletonContext _localctx = new SkeletonContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_skeleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(T__0);
			setState(19);
			((SkeletonContext)_localctx).j = joint();
			setState(20);
			match(T__1);
			 ((SkeletonContext)_localctx).r =  ((SkeletonContext)_localctx).j.r; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JointContext extends ParserRuleContext {
		public org.nxn.model.skeleton.ParsedJoint r;
		public Token nm;
		public Vector3Context vec;
		public Token a;
		public BindingsContext b;
		public JointListContext l;
		public List<TerminalNode> NAME() { return getTokens(SkeletonParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(SkeletonParser.NAME, i);
		}
		public Vector3Context vector3() {
			return getRuleContext(Vector3Context.class,0);
		}
		public BindingsContext bindings() {
			return getRuleContext(BindingsContext.class,0);
		}
		public JointListContext jointList() {
			return getRuleContext(JointListContext.class,0);
		}
		public JointContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joint; }
	}

	public final JointContext joint() throws RecognitionException {
		JointContext _localctx = new JointContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_joint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			((JointContext)_localctx).nm = match(NAME);
			setState(24);
			match(T__2);
			setState(25);
			((JointContext)_localctx).vec = vector3();
			setState(28);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(26);
				match(T__2);
				setState(27);
				((JointContext)_localctx).a = match(NAME);
				}
				break;
			}
			setState(32);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(30);
				match(T__2);
				setState(31);
				((JointContext)_localctx).b = bindings();
				}
				break;
			}
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(34);
				match(T__2);
				setState(35);
				((JointContext)_localctx).l = jointList();
				}
			}

			 ((JointContext)_localctx).r =  new org.nxn.model.skeleton.ParsedJoint( (((JointContext)_localctx).nm!=null?((JointContext)_localctx).nm.getText():null), ((JointContext)_localctx).vec.r, (((JointContext)_localctx).a!=null?((JointContext)_localctx).a.getText():null), ((JointContext)_localctx).b.r, ((JointContext)_localctx).l.r); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JointListContext extends ParserRuleContext {
		public org.nxn.model.skeleton.ParsedJoint[] r;
		public JointContext j;
		public JointContext k;
		public List<JointContext> joint() {
			return getRuleContexts(JointContext.class);
		}
		public JointContext joint(int i) {
			return getRuleContext(JointContext.class,i);
		}
		public JointListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jointList; }
	}

	public final JointListContext jointList() throws RecognitionException {
		JointListContext _localctx = new JointListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_jointList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.nxn.model.skeleton.ParsedJoint> l = new java.util.ArrayList<org.nxn.model.skeleton.ParsedJoint>(); 
			setState(41);
			match(T__0);
			setState(42);
			((JointListContext)_localctx).j = joint();
			 l.add(((JointListContext)_localctx).j.r); 
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(44);
				match(T__3);
				setState(45);
				((JointListContext)_localctx).k = joint();
				 l.add(((JointListContext)_localctx).k.r); 
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53);
			match(T__1);
			 ((JointListContext)_localctx).r =  l.toArray(new org.nxn.model.skeleton.ParsedJoint[0]); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BindingsContext extends ParserRuleContext {
		public org.nxn.model.skeleton.ParsedBinding[] r;
		public BindingContext n;
		public BindingContext m;
		public List<BindingContext> binding() {
			return getRuleContexts(BindingContext.class);
		}
		public BindingContext binding(int i) {
			return getRuleContext(BindingContext.class,i);
		}
		public BindingsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bindings; }
	}

	public final BindingsContext bindings() throws RecognitionException {
		BindingsContext _localctx = new BindingsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_bindings);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.nxn.model.skeleton.ParsedBinding> l = new java.util.ArrayList<org.nxn.model.skeleton.ParsedBinding>(); 
			setState(57);
			match(T__0);
			setState(58);
			((BindingsContext)_localctx).n = binding();
			 l.add(((BindingsContext)_localctx).n.r); 
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(60);
				match(T__3);
				setState(61);
				((BindingsContext)_localctx).m = binding();
				 l.add(((BindingsContext)_localctx).m.r); 
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(T__1);
			 ((BindingsContext)_localctx).r =  l.toArray( new org.nxn.model.skeleton.ParsedBinding[0] ); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BindingContext extends ParserRuleContext {
		public org.nxn.model.skeleton.ParsedBinding r;
		public Token n;
		public IndListContext i;
		public TerminalNode NAME() { return getToken(SkeletonParser.NAME, 0); }
		public IndListContext indList() {
			return getRuleContext(IndListContext.class,0);
		}
		public BindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binding; }
	}

	public final BindingContext binding() throws RecognitionException {
		BindingContext _localctx = new BindingContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_binding);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			((BindingContext)_localctx).n = match(NAME);
			setState(73);
			match(T__2);
			setState(74);
			((BindingContext)_localctx).i = indList();
			 ((BindingContext)_localctx).r =  new org.nxn.model.skeleton.ParsedBinding((((BindingContext)_localctx).n!=null?((BindingContext)_localctx).n.getText():null), ((BindingContext)_localctx).i.r); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndListContext extends ParserRuleContext {
		public int[] r;
		public IndNumContext n;
		public IndNumContext m;
		public List<IndNumContext> indNum() {
			return getRuleContexts(IndNumContext.class);
		}
		public IndNumContext indNum(int i) {
			return getRuleContext(IndNumContext.class,i);
		}
		public IndListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indList; }
	}

	public final IndListContext indList() throws RecognitionException {
		IndListContext _localctx = new IndListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_indList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.ArrayList<Integer> l = new java.util.ArrayList<Integer>(); 
			setState(78);
			match(T__0);
			setState(79);
			((IndListContext)_localctx).n = indNum();
			 l.add(((IndListContext)_localctx).n.r); 
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(81);
				match(T__3);
				setState(82);
				((IndListContext)_localctx).m = indNum();
				 l.add(((IndListContext)_localctx).m.r); 
				}
				}
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90);
			match(T__1);
			 ((IndListContext)_localctx).r =  l.stream().mapToInt(Integer::intValue).toArray(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Vector3Context extends ParserRuleContext {
		public org.nxn.math.Vector3f r;
		public FloatNumContext a;
		public FloatNumContext b;
		public FloatNumContext c;
		public List<FloatNumContext> floatNum() {
			return getRuleContexts(FloatNumContext.class);
		}
		public FloatNumContext floatNum(int i) {
			return getRuleContext(FloatNumContext.class,i);
		}
		public Vector3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector3; }
	}

	public final Vector3Context vector3() throws RecognitionException {
		Vector3Context _localctx = new Vector3Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_vector3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(T__4);
			setState(94);
			((Vector3Context)_localctx).a = floatNum();
			setState(95);
			match(T__3);
			setState(96);
			((Vector3Context)_localctx).b = floatNum();
			setState(97);
			match(T__3);
			setState(98);
			((Vector3Context)_localctx).c = floatNum();
			setState(99);
			match(T__5);
			 ((Vector3Context)_localctx).r =  new org.nxn.math.Vector3f(((Vector3Context)_localctx).a.r, ((Vector3Context)_localctx).b.r, ((Vector3Context)_localctx).c.r); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FloatNumContext extends ParserRuleContext {
		public float r;
		public Token s;
		public Token n;
		public Token m;
		public Token e;
		public Token p;
		public List<TerminalNode> DIGITS() { return getTokens(SkeletonParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(SkeletonParser.DIGITS, i);
		}
		public FloatNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatNum; }
	}

	public final FloatNumContext floatNum() throws RecognitionException {
		FloatNumContext _localctx = new FloatNumContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_floatNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6 || _la==T__7) {
				{
				setState(102);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__6 || _la==T__7) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(105);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(106);
				match(T__8);
				setState(107);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__10) {
				{
				setState(110);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6 || _la==T__7) {
					{
					setState(111);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__6 || _la==T__7) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(114);
				((FloatNumContext)_localctx).p = match(DIGITS);
				}
			}


			        StringBuilder sb = new StringBuilder();
			        if((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null) != null){
			            sb.append((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null));
			        }
			        sb.append((((FloatNumContext)_localctx).n!=null?((FloatNumContext)_localctx).n.getText():null));
			        if((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null) != null){
			            sb.append('.').append((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null));
			        }
			        if((((FloatNumContext)_localctx).p!=null?((FloatNumContext)_localctx).p.getText():null) != null){
			            sb.append('E');
			            if((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null) != null){
			                sb.append((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null));
			            }
			            sb.append((((FloatNumContext)_localctx).p!=null?((FloatNumContext)_localctx).p.getText():null));
			        }
			        ((FloatNumContext)_localctx).r =  Float.parseFloat(sb.toString());
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndNumContext extends ParserRuleContext {
		public int r;
		public Token n;
		public TerminalNode DIGITS() { return getToken(SkeletonParser.DIGITS, 0); }
		public IndNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indNum; }
	}

	public final IndNumContext indNum() throws RecognitionException {
		IndNumContext _localctx = new IndNumContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_indNum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			((IndNumContext)_localctx).n = match(DIGITS);
			 ((IndNumContext)_localctx).r =  Integer.parseInt((((IndNumContext)_localctx).n!=null?((IndNumContext)_localctx).n.getText():null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000e{\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"\u001d\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001!\b\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001%\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u00021\b\u0002\n\u0002\f\u00024\t\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003A\b\u0003"+
		"\n\u0003\f\u0003D\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005V\b\u0005\n\u0005\f\u0005Y\t\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0003\u0007"+
		"h\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007m\b\u0007\u0001"+
		"\u0007\u0001\u0007\u0003\u0007q\b\u0007\u0001\u0007\u0003\u0007t\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0000\u0000\t"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0000\u0002\u0001\u0000\u0007"+
		"\b\u0001\u0000\n\u000b{\u0000\u0012\u0001\u0000\u0000\u0000\u0002\u0017"+
		"\u0001\u0000\u0000\u0000\u0004(\u0001\u0000\u0000\u0000\u00068\u0001\u0000"+
		"\u0000\u0000\bH\u0001\u0000\u0000\u0000\nM\u0001\u0000\u0000\u0000\f]"+
		"\u0001\u0000\u0000\u0000\u000eg\u0001\u0000\u0000\u0000\u0010w\u0001\u0000"+
		"\u0000\u0000\u0012\u0013\u0005\u0001\u0000\u0000\u0013\u0014\u0003\u0002"+
		"\u0001\u0000\u0014\u0015\u0005\u0002\u0000\u0000\u0015\u0016\u0006\u0000"+
		"\uffff\uffff\u0000\u0016\u0001\u0001\u0000\u0000\u0000\u0017\u0018\u0005"+
		"\f\u0000\u0000\u0018\u0019\u0005\u0003\u0000\u0000\u0019\u001c\u0003\f"+
		"\u0006\u0000\u001a\u001b\u0005\u0003\u0000\u0000\u001b\u001d\u0005\f\u0000"+
		"\u0000\u001c\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000"+
		"\u0000\u001d \u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0003\u0000\u0000"+
		"\u001f!\u0003\u0006\u0003\u0000 \u001e\u0001\u0000\u0000\u0000 !\u0001"+
		"\u0000\u0000\u0000!$\u0001\u0000\u0000\u0000\"#\u0005\u0003\u0000\u0000"+
		"#%\u0003\u0004\u0002\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000"+
		"\u0000%&\u0001\u0000\u0000\u0000&\'\u0006\u0001\uffff\uffff\u0000\'\u0003"+
		"\u0001\u0000\u0000\u0000()\u0006\u0002\uffff\uffff\u0000)*\u0005\u0001"+
		"\u0000\u0000*+\u0003\u0002\u0001\u0000+2\u0006\u0002\uffff\uffff\u0000"+
		",-\u0005\u0004\u0000\u0000-.\u0003\u0002\u0001\u0000./\u0006\u0002\uffff"+
		"\uffff\u0000/1\u0001\u0000\u0000\u00000,\u0001\u0000\u0000\u000014\u0001"+
		"\u0000\u0000\u000020\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u0000"+
		"35\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000056\u0005\u0002\u0000"+
		"\u000067\u0006\u0002\uffff\uffff\u00007\u0005\u0001\u0000\u0000\u0000"+
		"89\u0006\u0003\uffff\uffff\u00009:\u0005\u0001\u0000\u0000:;\u0003\b\u0004"+
		"\u0000;B\u0006\u0003\uffff\uffff\u0000<=\u0005\u0004\u0000\u0000=>\u0003"+
		"\b\u0004\u0000>?\u0006\u0003\uffff\uffff\u0000?A\u0001\u0000\u0000\u0000"+
		"@<\u0001\u0000\u0000\u0000AD\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000\u0000\u0000DB\u0001\u0000"+
		"\u0000\u0000EF\u0005\u0002\u0000\u0000FG\u0006\u0003\uffff\uffff\u0000"+
		"G\u0007\u0001\u0000\u0000\u0000HI\u0005\f\u0000\u0000IJ\u0005\u0003\u0000"+
		"\u0000JK\u0003\n\u0005\u0000KL\u0006\u0004\uffff\uffff\u0000L\t\u0001"+
		"\u0000\u0000\u0000MN\u0006\u0005\uffff\uffff\u0000NO\u0005\u0001\u0000"+
		"\u0000OP\u0003\u0010\b\u0000PW\u0006\u0005\uffff\uffff\u0000QR\u0005\u0004"+
		"\u0000\u0000RS\u0003\u0010\b\u0000ST\u0006\u0005\uffff\uffff\u0000TV\u0001"+
		"\u0000\u0000\u0000UQ\u0001\u0000\u0000\u0000VY\u0001\u0000\u0000\u0000"+
		"WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000XZ\u0001\u0000\u0000"+
		"\u0000YW\u0001\u0000\u0000\u0000Z[\u0005\u0002\u0000\u0000[\\\u0006\u0005"+
		"\uffff\uffff\u0000\\\u000b\u0001\u0000\u0000\u0000]^\u0005\u0005\u0000"+
		"\u0000^_\u0003\u000e\u0007\u0000_`\u0005\u0004\u0000\u0000`a\u0003\u000e"+
		"\u0007\u0000ab\u0005\u0004\u0000\u0000bc\u0003\u000e\u0007\u0000cd\u0005"+
		"\u0006\u0000\u0000de\u0006\u0006\uffff\uffff\u0000e\r\u0001\u0000\u0000"+
		"\u0000fh\u0007\u0000\u0000\u0000gf\u0001\u0000\u0000\u0000gh\u0001\u0000"+
		"\u0000\u0000hi\u0001\u0000\u0000\u0000il\u0005\r\u0000\u0000jk\u0005\t"+
		"\u0000\u0000km\u0005\r\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000"+
		"\u0000\u0000ms\u0001\u0000\u0000\u0000np\u0007\u0001\u0000\u0000oq\u0007"+
		"\u0000\u0000\u0000po\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000"+
		"qr\u0001\u0000\u0000\u0000rt\u0005\r\u0000\u0000sn\u0001\u0000\u0000\u0000"+
		"st\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000uv\u0006\u0007\uffff"+
		"\uffff\u0000v\u000f\u0001\u0000\u0000\u0000wx\u0005\r\u0000\u0000xy\u0006"+
		"\b\uffff\uffff\u0000y\u0011\u0001\u0000\u0000\u0000\n\u001c $2BWglps";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}