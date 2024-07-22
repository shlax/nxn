// Generated from /home/pochodnicky/wrksp/no-git/NXN/src/g4/Model.g4 by ANTLR 4.13.1
package org.nxn.model.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ModelParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, DIGITS=14, WS=15;
	public static final int
		RULE_model = 0, RULE_face = 1, RULE_vertex = 2, RULE_uvs = 3, RULE_points = 4, 
		RULE_vector3 = 5, RULE_vector2 = 6, RULE_floatNum = 7, RULE_intNum = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "face", "vertex", "uvs", "points", "vector3", "vector2", "floatNum", 
			"intNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'['", "','", "']'", "':'", "'('", "')'", "'+'", 
			"'-'", "'.'", "'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "DIGITS", "WS"
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
	public String getGrammarFileName() { return "Model.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ModelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModelContext extends ParserRuleContext {
		public org.nxn.model.ParsedModel result;
		public PointsContext p;
		public FaceContext f;
		public FaceContext fi;
		public PointsContext points() {
			return getRuleContext(PointsContext.class,0);
		}
		public List<FaceContext> face() {
			return getRuleContexts(FaceContext.class);
		}
		public FaceContext face(int i) {
			return getRuleContext(FaceContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.nxn.model.ParsedFace> lf = new java.util.ArrayList<>(); 
			setState(19);
			((ModelContext)_localctx).p = points();
			setState(20);
			match(T__0);
			setState(21);
			((ModelContext)_localctx).f = face();
			 lf.add(((ModelContext)_localctx).f.r); 
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(23);
				((ModelContext)_localctx).fi = face();
				 lf.add(((ModelContext)_localctx).fi.r); 
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(T__1);
			 ((ModelContext)_localctx).result =  new org.nxn.model.ParsedModel( ((ModelContext)_localctx).p.r.toArray(new org.nxn.math.Vector3f[0] ), lf.toArray( new org.nxn.model.ParsedFace[0] ) ); 
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
	public static class FaceContext extends ParserRuleContext {
		public org.nxn.model.ParsedFace r;
		public VertexContext a;
		public VertexContext b;
		public VertexContext c;
		public List<VertexContext> vertex() {
			return getRuleContexts(VertexContext.class);
		}
		public VertexContext vertex(int i) {
			return getRuleContext(VertexContext.class,i);
		}
		public FaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_face; }
	}

	public final FaceContext face() throws RecognitionException {
		FaceContext _localctx = new FaceContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_face);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__2);
			setState(35);
			((FaceContext)_localctx).a = vertex();
			setState(36);
			match(T__3);
			setState(37);
			((FaceContext)_localctx).b = vertex();
			setState(38);
			match(T__3);
			setState(39);
			((FaceContext)_localctx).c = vertex();
			setState(40);
			match(T__4);
			 ((FaceContext)_localctx).r =  new org.nxn.model.ParsedFace(((FaceContext)_localctx).a.r, ((FaceContext)_localctx).b.r, ((FaceContext)_localctx).c.r); 
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
	public static class VertexContext extends ParserRuleContext {
		public org.nxn.model.ParsedVertex r;
		public IntNumContext i;
		public Vector3Context n;
		public UvsContext u;
		public IntNumContext intNum() {
			return getRuleContext(IntNumContext.class,0);
		}
		public Vector3Context vector3() {
			return getRuleContext(Vector3Context.class,0);
		}
		public UvsContext uvs() {
			return getRuleContext(UvsContext.class,0);
		}
		public VertexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertex; }
	}

	public final VertexContext vertex() throws RecognitionException {
		VertexContext _localctx = new VertexContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_vertex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			((VertexContext)_localctx).i = intNum();
			setState(44);
			match(T__5);
			setState(45);
			((VertexContext)_localctx).n = vector3();
			setState(46);
			match(T__5);
			setState(47);
			((VertexContext)_localctx).u = uvs();
			 ((VertexContext)_localctx).r =  new org.nxn.model.ParsedVertex(((VertexContext)_localctx).i.r, ((VertexContext)_localctx).n.r, ((VertexContext)_localctx).u.r.toArray( new org.nxn.math.Vector2f[0] ) ); 
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
	public static class UvsContext extends ParserRuleContext {
		public java.util.List<org.nxn.math.Vector2f> r;
		public Vector2Context p;
		public Vector2Context pi;
		public List<Vector2Context> vector2() {
			return getRuleContexts(Vector2Context.class);
		}
		public Vector2Context vector2(int i) {
			return getRuleContext(Vector2Context.class,i);
		}
		public UvsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uvs; }
	}

	public final UvsContext uvs() throws RecognitionException {
		UvsContext _localctx = new UvsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_uvs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((UvsContext)_localctx).r =  new java.util.ArrayList<>(); 
			setState(51);
			match(T__2);
			setState(52);
			((UvsContext)_localctx).p = vector2();
			 _localctx.r.add(((UvsContext)_localctx).p.r); 
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(54);
				match(T__3);
				setState(55);
				((UvsContext)_localctx).pi = vector2();
				 _localctx.r.add(((UvsContext)_localctx).pi.r); 
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
			match(T__4);
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
	public static class PointsContext extends ParserRuleContext {
		public java.util.List<org.nxn.math.Vector3f> r;
		public Vector3Context p;
		public Vector3Context pi;
		public List<Vector3Context> vector3() {
			return getRuleContexts(Vector3Context.class);
		}
		public Vector3Context vector3(int i) {
			return getRuleContext(Vector3Context.class,i);
		}
		public PointsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_points; }
	}

	public final PointsContext points() throws RecognitionException {
		PointsContext _localctx = new PointsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_points);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((PointsContext)_localctx).r =  new java.util.ArrayList<>(); 
			setState(66);
			match(T__2);
			setState(67);
			((PointsContext)_localctx).p = vector3();
			 _localctx.r.add(((PointsContext)_localctx).p.r); 
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(69);
				match(T__3);
				setState(70);
				((PointsContext)_localctx).pi = vector3();
				 _localctx.r.add(((PointsContext)_localctx).pi.r); 
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(T__4);
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
		enterRule(_localctx, 10, RULE_vector3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__6);
			setState(81);
			((Vector3Context)_localctx).a = floatNum();
			setState(82);
			match(T__3);
			setState(83);
			((Vector3Context)_localctx).b = floatNum();
			setState(84);
			match(T__3);
			setState(85);
			((Vector3Context)_localctx).c = floatNum();
			setState(86);
			match(T__7);
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
	public static class Vector2Context extends ParserRuleContext {
		public org.nxn.math.Vector2f r;
		public FloatNumContext a;
		public FloatNumContext b;
		public List<FloatNumContext> floatNum() {
			return getRuleContexts(FloatNumContext.class);
		}
		public FloatNumContext floatNum(int i) {
			return getRuleContext(FloatNumContext.class,i);
		}
		public Vector2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector2; }
	}

	public final Vector2Context vector2() throws RecognitionException {
		Vector2Context _localctx = new Vector2Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_vector2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__6);
			setState(90);
			((Vector2Context)_localctx).a = floatNum();
			setState(91);
			match(T__3);
			setState(92);
			((Vector2Context)_localctx).b = floatNum();
			setState(93);
			match(T__7);
			 ((Vector2Context)_localctx).r =  new org.nxn.math.Vector2f(((Vector2Context)_localctx).a.r, ((Vector2Context)_localctx).b.r); 
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
		public List<TerminalNode> DIGITS() { return getTokens(ModelParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(ModelParser.DIGITS, i);
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
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(96);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(99);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(100);
				match(T__10);
				setState(101);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11 || _la==T__12) {
				{
				setState(104);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8 || _la==T__9) {
					{
					setState(105);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__8 || _la==T__9) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(108);
				((FloatNumContext)_localctx).p = match(DIGITS);
				}
			}


			        StringBuilder sb = new StringBuilder();
			        if((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null) != null) sb.append((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null));
			        sb.append((((FloatNumContext)_localctx).n!=null?((FloatNumContext)_localctx).n.getText():null));
			        if((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null) != null){
			            sb.append('.').append((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null));
			        }
			        if((((FloatNumContext)_localctx).p!=null?((FloatNumContext)_localctx).p.getText():null) != null){
			            sb.append('E');
			            if((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null) != null) sb.append((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null));
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
	public static class IntNumContext extends ParserRuleContext {
		public int r;
		public Token s;
		public Token n;
		public TerminalNode DIGITS() { return getToken(ModelParser.DIGITS, 0); }
		public IntNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intNum; }
	}

	public final IntNumContext intNum() throws RecognitionException {
		IntNumContext _localctx = new IntNumContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_intNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(113);
				((IntNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
					((IntNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(116);
			((IntNumContext)_localctx).n = match(DIGITS);
			 ((IntNumContext)_localctx).r =  Integer.parseInt((((IntNumContext)_localctx).s!=null?((IntNumContext)_localctx).s.getText():null) == null ? (((IntNumContext)_localctx).n!=null?((IntNumContext)_localctx).n.getText():null) : (((IntNumContext)_localctx).s!=null?((IntNumContext)_localctx).s.getText():null) + (((IntNumContext)_localctx).n!=null?((IntNumContext)_localctx).n.getText():null) ); 
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
		"\u0004\u0001\u000fx\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000"+
		"\f\u0000\u001e\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003"+
		";\b\u0003\n\u0003\f\u0003>\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0005\u0004J\b\u0004\n\u0004\f\u0004M\t\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0003\u0007b\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007"+
		"g\b\u0007\u0001\u0007\u0001\u0007\u0003\u0007k\b\u0007\u0001\u0007\u0003"+
		"\u0007n\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0003\bs\b\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0000\u0000\t\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0000\u0002\u0001\u0000\t\n\u0001\u0000\f\rv\u0000\u0012\u0001\u0000"+
		"\u0000\u0000\u0002\"\u0001\u0000\u0000\u0000\u0004+\u0001\u0000\u0000"+
		"\u0000\u00062\u0001\u0000\u0000\u0000\bA\u0001\u0000\u0000\u0000\nP\u0001"+
		"\u0000\u0000\u0000\fY\u0001\u0000\u0000\u0000\u000ea\u0001\u0000\u0000"+
		"\u0000\u0010r\u0001\u0000\u0000\u0000\u0012\u0013\u0006\u0000\uffff\uffff"+
		"\u0000\u0013\u0014\u0003\b\u0004\u0000\u0014\u0015\u0005\u0001\u0000\u0000"+
		"\u0015\u0016\u0003\u0002\u0001\u0000\u0016\u001c\u0006\u0000\uffff\uffff"+
		"\u0000\u0017\u0018\u0003\u0002\u0001\u0000\u0018\u0019\u0006\u0000\uffff"+
		"\uffff\u0000\u0019\u001b\u0001\u0000\u0000\u0000\u001a\u0017\u0001\u0000"+
		"\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000\u001c\u001a\u0001\u0000"+
		"\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001f\u0001\u0000"+
		"\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001f \u0005\u0002\u0000"+
		"\u0000 !\u0006\u0000\uffff\uffff\u0000!\u0001\u0001\u0000\u0000\u0000"+
		"\"#\u0005\u0003\u0000\u0000#$\u0003\u0004\u0002\u0000$%\u0005\u0004\u0000"+
		"\u0000%&\u0003\u0004\u0002\u0000&\'\u0005\u0004\u0000\u0000\'(\u0003\u0004"+
		"\u0002\u0000()\u0005\u0005\u0000\u0000)*\u0006\u0001\uffff\uffff\u0000"+
		"*\u0003\u0001\u0000\u0000\u0000+,\u0003\u0010\b\u0000,-\u0005\u0006\u0000"+
		"\u0000-.\u0003\n\u0005\u0000./\u0005\u0006\u0000\u0000/0\u0003\u0006\u0003"+
		"\u000001\u0006\u0002\uffff\uffff\u00001\u0005\u0001\u0000\u0000\u0000"+
		"23\u0006\u0003\uffff\uffff\u000034\u0005\u0003\u0000\u000045\u0003\f\u0006"+
		"\u00005<\u0006\u0003\uffff\uffff\u000067\u0005\u0004\u0000\u000078\u0003"+
		"\f\u0006\u000089\u0006\u0003\uffff\uffff\u00009;\u0001\u0000\u0000\u0000"+
		":6\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000"+
		"\u0000<=\u0001\u0000\u0000\u0000=?\u0001\u0000\u0000\u0000><\u0001\u0000"+
		"\u0000\u0000?@\u0005\u0005\u0000\u0000@\u0007\u0001\u0000\u0000\u0000"+
		"AB\u0006\u0004\uffff\uffff\u0000BC\u0005\u0003\u0000\u0000CD\u0003\n\u0005"+
		"\u0000DK\u0006\u0004\uffff\uffff\u0000EF\u0005\u0004\u0000\u0000FG\u0003"+
		"\n\u0005\u0000GH\u0006\u0004\uffff\uffff\u0000HJ\u0001\u0000\u0000\u0000"+
		"IE\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000"+
		"\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001\u0000"+
		"\u0000\u0000NO\u0005\u0005\u0000\u0000O\t\u0001\u0000\u0000\u0000PQ\u0005"+
		"\u0007\u0000\u0000QR\u0003\u000e\u0007\u0000RS\u0005\u0004\u0000\u0000"+
		"ST\u0003\u000e\u0007\u0000TU\u0005\u0004\u0000\u0000UV\u0003\u000e\u0007"+
		"\u0000VW\u0005\b\u0000\u0000WX\u0006\u0005\uffff\uffff\u0000X\u000b\u0001"+
		"\u0000\u0000\u0000YZ\u0005\u0007\u0000\u0000Z[\u0003\u000e\u0007\u0000"+
		"[\\\u0005\u0004\u0000\u0000\\]\u0003\u000e\u0007\u0000]^\u0005\b\u0000"+
		"\u0000^_\u0006\u0006\uffff\uffff\u0000_\r\u0001\u0000\u0000\u0000`b\u0007"+
		"\u0000\u0000\u0000a`\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000"+
		"bc\u0001\u0000\u0000\u0000cf\u0005\u000e\u0000\u0000de\u0005\u000b\u0000"+
		"\u0000eg\u0005\u000e\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001\u0000"+
		"\u0000\u0000gm\u0001\u0000\u0000\u0000hj\u0007\u0001\u0000\u0000ik\u0007"+
		"\u0000\u0000\u0000ji\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000"+
		"kl\u0001\u0000\u0000\u0000ln\u0005\u000e\u0000\u0000mh\u0001\u0000\u0000"+
		"\u0000mn\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000op\u0006\u0007"+
		"\uffff\uffff\u0000p\u000f\u0001\u0000\u0000\u0000qs\u0007\u0000\u0000"+
		"\u0000rq\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0001\u0000"+
		"\u0000\u0000tu\u0005\u000e\u0000\u0000uv\u0006\b\uffff\uffff\u0000v\u0011"+
		"\u0001\u0000\u0000\u0000\b\u001c<Kafjmr";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}