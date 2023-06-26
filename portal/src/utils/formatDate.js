/**
 * 获取当前日期
 *
 * @returns {string} 当前时间
 */
export function getDate(){
    const time = new Date()
    const year = time.getFullYear()
    let month = time.getMonth() + 1
    if (month < 10) {
        month = '0' + month;
    }
    let day = time.getDate();
    if (day < 10) {
        day = '0' + day;
    }
    let hour = time.getHours();
    if (hour < 10) {
        hour = '0' + hour;
    }
    let minute  = time.getMinutes();
    if (minute < 10) {
        minute = '0' + minute;
    }
    let second = time.getSeconds()
    if (second < 10) {
        second = '0' + second;
    }
    const week = '星期' + '日一二三四五六'.charAt(time.getDay())
    return `${year}-${month}-${day} ${hour}:${minute}:${second} ${week}`;
}

/**
 * 获取当前时间段
 * @returns {string} 早中下晚
 */
export function getDayOfPeriod(){
    const time = new Date();
    let hour = time.getHours();
    if (hour >=7 && hour < 12) {
        return "早上";
    } else if (hour >=12 && hour < 14) {
        return "中午";
    } else if (hour >= 14 && hour < 19) {
        return "下午";
    } else {
        return "晚上";
    }
}
/**
 * 获取今天是周几
 *
 * @returns {string} 星期几
 */
export function getWeek(){
    const time = new Date()
    return '星期' + '日一二三四五六'.charAt(time.getDay());
}