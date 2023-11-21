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

        function getNotificationFromChannel() {
            const userId = document.getElementById('userId').value;
            const pusher = new Pusher('45b9b41cab6ad01f6264', {
                cluster: 'ap1'
            });

            console.log('userId', userId)

            const channel = pusher.subscribe(`my-channel-${userId}`);
            channel.bind(`noti-event`, function (data) {
                // playSound();
                let notification = JSON.parse(data);
                let dateArray = notification.date;
                notification.date = new Date(dateArray[0], dateArray[1], dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6]);
                vendor.notifications.push(notification);
                buildNotificationListItem();
                if (notify) {
                    notify(notification);
                }

            });
        }


        function buildNotificationListItem() {
            $this.empty();
            //sort by date desc
            const newNoti = vendor.notifications.filter(val => !val.isRead).sort((a, b) => new Date(b.date) - new Date(a.date));
            $('#noti-badge').text(newNoti.length)
            if (newNoti.length > 0) {
                $('#noti-badge').show();
            } else {
                $('#noti-badge').hide();
            }

            $this.append(`<li id="noti-item-layout" style="max-height: 200px; overflow-y: scroll;"></li>`);

            newNoti.forEach((noti,i) => {
                let since = timeSince(noti.date);
                const icon = noti.type === 'TASK' ? 'bi-list-task' : 'bi-chat-left-text-fill';
                let notiItem = `<a id="${i}-noti-drop" href="${noti.link}">
                                <li class="notification-item" style="cursor: pointer; min-height: 90px;">
                                <i class="bi ${icon} text-warning"></i>
                                <div>
                                    <p class="text-dark fw-bold">${noti.message}</p>
                                    <p style="font-size: 12px">${since}</p>
                                </div>
                            </li></a>
                            <hr class="dropdown-divider">`;
                $('#noti-item-layout').append(notiItem);
                $(`#${i}-noti-drop`).click(function () {
                    markNotificationAsRead(noti.id);
                });
            });


            let footer = `<li class="position-fixed bottom-0 dropdown-footer w-100 bg-white">
                                    <a href="/notification">Show all notifications</a>
                                </li>`;

            $this.append(footer);
        }

        init();
    }
})(jQuery);

// function playSound() {
//     let audio = new Audio(`/audio/noti.wav`);
//     audio.play();
// }

function markNotificationAsRead(id) {
    // Make an AJAX request to mark the notification as read
    $.ajax({
        url: "/mark-as-read/" + id, // Include the id in the URL
        type: "POST",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            // Optionally, you can update the UI or perform additional actions after marking as read
        },
        error: function (error) {
            console.error("Error marking notification as read:", error);
            // Handle the error or display a message to the user
        }
    });
}

function timeSince(date) {
    const seconds = Math.floor((new Date() - new Date(date)) / 1000);
    let interval = Math.floor(seconds / 86400);

    if (interval >= 1) {
        return interval + " days ago";
    }

    interval = Math.floor(seconds / 3600);
    if (interval >= 1) {
        return interval + " hours ago";
    }

    interval = Math.floor(seconds / 60);
    if (interval >= 1) {
        return interval + " minutes ago";
    }

    return "Less than a minute ago";
}