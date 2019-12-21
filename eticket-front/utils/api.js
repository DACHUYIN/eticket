// 会员后端接口基础路径
export const MEMBER_API_URL = 'http://127.0.0.1:6076';
// 登录地址
export const LOGIN_URL = MEMBER_API_URL + '/member/login';
// 更新当前登录用户的个人信息
export const UPDATE_LOGIN_USER_URL = MEMBER_API_URL + '/member/updateLoginUserInfo';
// 交易后端接口基础路径
export const TRASACTION_API_URL = 'http://127.0.0.1:6077';
// 从服务器端获取幂等性token
export const GET_IDEMPOTENT_TOKEN = TRASACTION_API_URL + '/transaction/getIdempotentToken';
// 上传图片的地址
export const UPLOAD_FILE_URL = TRASACTION_API_URL + '/transaction/uploadFile';
// 确认电子券码
export const SUBMIT_ETICKET_URL = TRASACTION_API_URL + '/transaction/submitEticket';