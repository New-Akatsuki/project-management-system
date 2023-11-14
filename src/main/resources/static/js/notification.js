
const pusher = new Pusher('45b9b41cab6ad01f6264', {
    cluster: 'ap1'
});

const channel = pusher.subscribe(`my-channel-`);
channel.bind(`noti-event`, function(data) {
    alert(JSON.stringify(data));
});