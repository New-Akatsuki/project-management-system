(function ($) {
    $.fn.notification = function (options) {
        let $this = $(this);

        let vendor = $.extend(
            {
                notifications: [],
            },
            options
        );

        function init() {
            getNotificationFromChannel();
            buildNotificationListItem();
        }

        function getNotificationFromChannel(){
            const userId = document.getElementById('userId').value;
            const pusher = new Pusher('45b9b41cab6ad01f6264', {
                cluster: 'ap1'
            });

            console.log('userId', userId)

            const channel = pusher.subscribe(`my-channel-${userId}`);
            channel.bind(`noti-event`, function (data) {
                let notification = JSON.parse(data);
                let dateArray = notification.date;
                notification.date = new Date(dateArray[0]+'-'+dateArray[1]+'-'+dateArray[2]);
                vendor.notifications.push(notification);
                buildNotificationListItem();
            });
        }

        function buildNotificationHeader(count){
            let header = `<li class="dropdown-header">
                                    You have ${count} new notifications
                                    <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>`;
            $this.append(header);
        }

        function buildNotificationListItem(){
            $this.empty();
            vendor.notifications.forEach( noti => {
                let notiItem = `<li class="notification-item">
                                <i class="bi bi-exclamation-circle text-warning"></i>
                                <div>
                                    <h4>Title</h4>
                                    <p>${noti.message}</p>
                                    <p>30 min. ago</p>
                                </div>
                            </li>`;
                $this.append(notiItem);
            });


            let footer = `<li class="dropdown-footer">
                                    <a href="/notification">Show all notifications</a>
                                </li>`;

            $this.append(footer);
        }

        init();
    }
})(jQuery);
//
// const userId = document.getElementById('userId').value;
// const pusher = new Pusher('45b9b41cab6ad01f6264', {
//     cluster: 'ap1'
// });
//
// console.log('userId', userId)
//
// const channel = pusher.subscribe(`my-channel-${userId}`);
// channel.bind(`noti-event`, function (data) {});
//
// let htmlContainer = `<li class="dropdown-header">
//                                     You have 4 new notifications
//                                     <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
//                                 </li>
//                                 <li>
//                                     <hr class="dropdown-divider">
//                                 </li>
//
//                                 <li class="notification-item">
//                                     <i class="bi bi-exclamation-circle text-warning"></i>
//                                     <div>
//                                         <h4>Lorem Ipsum</h4>
//                                         <p>Quae dolorem earum veritatis oditseno</p>
//                                         <p>30 min. ago</p>
//                                     </div>
//                                 </li>
//
//                                 <li>
//                                     <hr class="dropdown-divider">
//                                 </li>
//                                 <li class="dropdown-footer">
//                                     <a href="/notification">Show all notifications</a>
//                                 </li>`;