package ee.ria.common.exception;

public class DeviceIdNotFoundException extends AuthServerException {
    public DeviceIdNotFoundException(String deviceId) {
        super(deviceId);
    }
}
