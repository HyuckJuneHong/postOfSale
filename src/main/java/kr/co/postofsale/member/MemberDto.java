package kr.co.postofsale.member;

public class MemberDto {

    public static class CREATE{

        private String identity;
        private String password;
        private String checkPassword;
        private MemberRole memberRole;

        public String getIdentity() {
            return identity;
        }

        public String getPassword() {
            return password;
        }

        public MemberRole getMemberRole() {
            return memberRole;
        }

        public String getCheckPassword() {
            return checkPassword;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setCheckPassword(String checkPassword) {
            this.checkPassword = checkPassword;
        }

        public void setMemberRole(MemberRole memberRole) {
            this.memberRole = memberRole;
        }
    }

    public static class UPDATE{
        private String identity;
        private String oldPassword;
        private String newPassword;
        private String checkPassword;

        public String getIdentity() {
            return identity;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public String getCheckPassword() {
            return checkPassword;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public void setCheckPassword(String checkPassword) {
            this.checkPassword = checkPassword;
        }
    }

    public static class DELETE{
        private String identity;
        private String password;

        public String getIdentity() {
            return identity;
        }

        public String getPassword() {
            return password;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
