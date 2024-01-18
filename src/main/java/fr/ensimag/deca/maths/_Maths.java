package maths;

public class _Maths {
    public static float _add(float a, float b) {
        return a + b;
    }

    public static float _sub(float a, float b) {
        return a - b;
    }

    public static float _mult(float a, float b) {
        return a * b;
    }

    public static float _div(float a, float b) {
        return a / b;
    }

    public static float _mod(float a, float b) {
        return a % b;
    }

    public static float _pow(float a, float b) {
        float res = 1;
        if (b == 0) {
            return 1;
        }
        if (b < 0) {
            for (int i = 0; i < -b; i++) {
                res = _div(res, a);
            }
            return res;
        }
        for (int i = 0; i < b; i++) {
            res = _mult(res, a);
        }
        return res;
    }

    public static float _abs(float a) {
        return a < 0 ? -a : a;
    }

    public static float _fact(float a) {
        float res = 1;
        for (int i = 1; i <= a; i++) {
            res = _mult(res, i);
        }
        return res;
    }

    public static float _sqrt(float a) {
        float x = a;
        float y = 1;
        float e = 0.000001f;
        while (x - y > e) {
            x = (x + y) / 2;
            y = a / x;
        }
        return x;
    }

    public static int _pow32(float f) {
        if (f == 0.0f) {
            return 0;
        }
        if (f < 0) {
            f = -f;
        }
        int e = 0;
        while (f < 1) {
            f *= 2;
            e--;
        }
        while (f >= 2) {
            f /= 2;
            e++;
        }
        return e;

    }

    public static float _log2(float a) {
        float res = 0;
        float x = 1;
        float e = 0.000001f;
        int i = 0;
        while (_abs(x) > e) {
            x = _div(_Maths._pow(_sub(a, 1), i + 1), i + 1);
            res = _add(res, x);
            i++;
        }
        return res;
    }

}
