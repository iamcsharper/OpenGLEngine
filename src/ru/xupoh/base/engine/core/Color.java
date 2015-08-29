package ru.xupoh.base.engine.core;

import java.util.Random;

public class Color {
	public static final Color WHITE = new Color(1);
	public static final Color BLACK = new Color(0);
	public static final Color CYAN = new Color(0, 1, 1);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color PINK = new Color(0.5f, 0, 0);
	public static final Color LIGHT_YELLOW = new Color(1f, 0.973f, 0.36506f);
	public static final Color LIGHT_GREEN = new Color(0.564f, 0.94f, 0.568f);
	public static final Color GREEN = new Color(0, 1, 0);

	public static class ColorHSB {
		private float h, s, b;

		public ColorHSB(float h, float s, float b) {
			this.h = h;
			this.s = s;
			this.b = b;
		}
		
		@Override
		public String toString() {
			return "ColorHBS (" + h + ", " + s + ", " + b + ")";
		}

		public Color getAsRGB() {
			int i = 0;
			float f, p, q, t = 0.0f;
			float tH = h, tS = s, tB = b;

			if (s == 0) {
				return new Color(b).getGrayscaled();
			}

			tH /= 60; // from 0 to 5
			i = (int) Math.floor(tH);
			f = h - i;
			p = tB * (1 - tS);
			q = tB * (1 - tS * f);
			t = tB * (1 - tS * (1 - f));

			if (i == 0) {
				return new Color(b, t, p);
			} else if (i == 1) {
				return new Color(q, b, p);
			} else if (i == 2) {
				return new Color(p, b, t);
			} else if (i == 3) {
				return new Color(p, q, b);
			} else if (i == 4) {
				return new Color(t, p, b);
			}

			return new Color(b, p, q);
		}

		public float getHue() {
			return h;
		}

		public float getSaturation() {
			return s;
		}

		public float getBrightness() {
			return b;
		}

		public void setHue(float h) {
			this.h = h;
		}

		public void setSaturation(float s) {
			this.s = s;
		}

		public void setBrightness(float b) {
			this.b = b;
		}
	}

	private float r, g, b, a;

	public Color() {
		this(0);
	}

	public Color(float gray) {
		this(gray, gray, gray);
	}

	public Color.ColorHSB getAsHBS() {
		Color.ColorHSB result = new Color.ColorHSB(0, 0, 0);
		float min, max, delta;
		min = Mathf.min( r, g, b );
		max = Mathf.max( r, g, b );
		result.setBrightness(max);
		delta = max - min;
		
		if( max != 0 )
			result.setSaturation(delta / max);		// s
		else {
			result.setHue(-1);
			return result;
		}
		
		if( r == max )
			result.setHue (( g - b ) / delta);
		else if( g == max )
			result.setHue( 2 + ( b - r ) / delta);
		else
			result.setHue( 4 + ( r - g ) / delta);
		result.setHue(result.getHue() * 60);
		if( result.getHue() < 0 )
			result.setHue(result.getHue() + 360);
		
		return result;
	}

	public Color(float r, float g, float b, float a) {
		this.r = Mathf.clamp(0, 1, r);
		this.g = Mathf.clamp(0, 1, g);
		this.b = Mathf.clamp(0, 1, b);
		this.a = Mathf.clamp(0, 1, a);
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 1);
	}

	public void grayScale() {
		float gray = (r + g + b) / 3;

		this.r = gray;
		this.g = gray;
		this.b = gray;
	}

	public Color clone() {
		return new Color(r, g, b, a);
	}

	public Color getGrayscaled() {
		Color res = this.clone();
		res.grayScale();

		return res;
	}

	public float getRed() {
		return r;
	}

	public void setRed(float r) {
		this.r = r;
	}

	public float getGreen() {
		return g;
	}

	public void setGreen(float g) {
		this.g = g;
	}

	public float getBlue() {
		return b;
	}

	public void setBlue(float b) {
		this.b = b;
	}

	public float getAlpha() {
		return a;
	}

	public void setAlpha(float a) {
		this.a = a;
	}

	public Vector3f toVector3f() {
		return new Vector3f(r, g, b);
	}

	public static Color lerp(Color a, Color b, float t) {
		float a_ = Mathf.smoothLerp(a.a, b.a, t);
		float r_ = Mathf.smoothLerp(a.r, b.r, t);
		float g_ = Mathf.smoothLerp(a.g, b.g, t);
		float b_ = Mathf.smoothLerp(a.b, b.b, t);

		return new Color(r_, g_, b_, a_);
	}

	public Color clampAll(float from, float to) {
		this.a = Mathf.clamp(from, to, a);
		this.r = Mathf.clamp(from, to, r);
		this.g = Mathf.clamp(from, to, g);
		this.b = Mathf.clamp(from, to, b);

		return this;
	}

	public static Color getRandom() {
		Random rnd = new Random();

		float red = rnd.nextFloat();
		float green = rnd.nextFloat();
		float blue = rnd.nextFloat();

		return new Color(red, green, blue);
	}

	public Color mul(Color other) {
		return new Color((other.r + r) / 2, (other.g + g) / 2,
				(other.b + b) / 2, (other.a + a) / 2);
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
}