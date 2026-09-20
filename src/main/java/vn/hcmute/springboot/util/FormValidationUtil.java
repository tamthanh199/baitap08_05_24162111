package vn.hcmute.springboot.util;

import java.util.regex.Pattern;

public final class FormValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9._]{4,30}$");
    private static final Pattern OTP_PATTERN = Pattern.compile("^\\d{6}$");

    private FormValidationUtil() { }

    public static String validateRegister(String fullName, String email, String phone,
            String username, String password, String confirmPassword) {
        if (isBlank(fullName) || isBlank(email) || isBlank(phone) || isBlank(username)
                || isBlank(password) || isBlank(confirmPassword)) return "Vui lòng nhập đầy đủ thông tin.";
        if (fullName.length() < 2 || fullName.length() > 100) return "Họ tên phải từ 2 đến 100 ký tự.";
        if (!EMAIL_PATTERN.matcher(email).matches()) return "Email không đúng định dạng.";
        if (email.length() > 150) return "Email không được vượt quá 150 ký tự.";
        if (!PHONE_PATTERN.matcher(phone).matches()) return "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0.";
        if (!USERNAME_PATTERN.matcher(username).matches()) return "Tài khoản phải từ 4 đến 30 ký tự và chỉ chứa chữ, số, dấu chấm hoặc gạch dưới.";
        if (password.length() < 6 || password.length() > 100) return "Mật khẩu phải từ 6 đến 100 ký tự.";
        if (!password.equals(confirmPassword)) return "Xác nhận mật khẩu không khớp.";
        return null;
    }

    public static String validateEmail(String email) {
        if (isBlank(email)) return "Vui lòng nhập email.";
        if (email.length() > 150 || !EMAIL_PATTERN.matcher(email).matches()) return "Email không đúng định dạng.";
        return null;
    }

    public static String validateResetPassword(String password, String confirmPassword) {
        if (isBlank(password) || isBlank(confirmPassword)) return "Vui lòng nhập đầy đủ mật khẩu.";
        if (password.length() < 6 || password.length() > 100) return "Mật khẩu phải từ 6 đến 100 ký tự.";
        if (!password.equals(confirmPassword)) return "Xác nhận mật khẩu không khớp.";
        return null;
    }

    public static String validateOtp(String otp) {
        if (isBlank(otp)) return "Vui lòng nhập mã OTP.";
        if (!OTP_PATTERN.matcher(otp.trim()).matches()) return "Mã OTP phải gồm đúng 6 chữ số.";
        return null;
    }

    public static String validateProfile(String fullName, String phone) {
        if (isBlank(fullName)) return "Họ và tên không được để trống.";
        if (fullName.trim().length() < 2 || fullName.trim().length() > 100) return "Họ và tên phải từ 2 đến 100 ký tự.";
        if (!isBlank(phone) && !PHONE_PATTERN.matcher(phone.trim()).matches()) return "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0.";
        return null;
    }

    private static boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
