export function Format (date) { 
  let month = '' + (date.getMonth() + 1)
  let day = '' + date.getDate()
  const year = date.getFullYear()
  let minute = ('0' + date.getMinutes()).slice(-2)
  let hour = ('0' + date.getHours()).slice(-2)
  let seconds = ('0' + date.getSeconds()).slice(-2)

  if (minute.length < 2) minute = '0' + minute
  if (hour.length < 2) hour = '0' + hour
  if (month.length < 2) month = '0' + month
  if (day.length < 2) day = '0' + day

  return [year, month, day].join('-') +' '+ [hour, minute, seconds].join(':')
}
