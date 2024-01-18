package maths;

public class MathsDeca extends _Maths {
    public static float pi = 3.14159265359f;

    public static float ulp(float f) {
        if (f == 0.0f) {
            return _Maths._pow(2, -149);
        }
        return _Maths._pow(2, _Maths._pow32(f)) * _Maths._pow(2, -23);
    }

    public static float cos(float a) {
        float ulp = ulp(a);
        float res = 0;
        float x = 1;
        int i = 0;
        while (_Maths._abs(x) > ulp) {
            x = _Maths._mult(_Maths._pow(-1, i), _Maths._div(_Maths._pow(a, 2 * i), _Maths._fact(2 * i)));
            res = _Maths._add(res, x);
            i++;
        }
        return res;
    }

    public static float sin(float a) {
        float ulp = ulp(a);
        float res = 0;
        float x = 1;
        int i = 0;
        while (_Maths._abs(x) > ulp) {
            x = _Maths._mult(_Maths._pow(-1, i), _Maths._div(_Maths._pow(a, 2 * i + 1), _Maths._fact(2 * i + 1)));
            res = _Maths._add(res, x);
            i++;
        }
        return res;
    }

    public static float tan(float a) {
        return _Maths._div(sin(a), cos(a));
    }

    public static float exp(float a) {
        float res = 0;
        float x = 1;
        float ulp = ulp(a);
        int i = 0;
        while (_Maths._abs(x) > ulp) {
            x = _Maths._div(_Maths._pow(a, i), _Maths._fact(i));
            res = _Maths._add(res, x);
            i++;
        }
        return res;
    }

    public static float arcsin(float a) {
        if (_Maths._abs(a) > 1) {
            throw new IllegalArgumentException("arcsin(a) is not defined for a > 1 or a < -1");
        }
        if (a == 1) {
            return pi / 2;
        }
        if (a == -1) {
            return -pi / 2;
        }

        float res = 0;
        float x = 1;
        float ulp = ulp(a);
        int i = 0;

        while (_Maths._abs(x) > ulp) {

            x = _Maths._mult(_Maths._div(_Maths._fact(2 * i), _Maths._pow(_Maths._fact(i) * _Maths._pow(2, i), 2)),
                    _Maths._div(_Maths._pow(a, 2 * i + 1), 2 * i + 1));
            res = _Maths._add(res, x);
            i++;
        }
        return res;
    }

    public static float arctan(float a) {
        if (a == 1) {
            return pi / 4;
        }
        if (a == -1) {
            return -pi / 4;
        }
        if (_Maths._abs(a) > 1) {
            return pi / 2 - arctan(_Maths._div(1, a));
        }
        float res = 0;
        float x = 1;
        float ulp = ulp(a);
        int i = 0;
        while (_Maths._abs(x) > ulp) {
            x = _Maths._mult(_Maths._pow(-1, i), _Maths._div(_Maths._pow(a, 2 * i + 1), 2 * i + 1));
            res = _Maths._add(res, x);
            i++;
        }
        return res;
    }
}
