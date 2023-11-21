function buildToast(data={
    title: 'Error!',
    body: 'something went wrong :(',
    icon:'bx bx-error-circle',
    color:'text-danger'
}) {

    $('#toastPlace').empty();
    const toast = `<div class="toast-container position-fixed toast-place">
                                      <div id="liveToast" class="toast bg-white" role="alert" aria-live="assertive" aria-atomic="true">
                                        <div class="toast-header">
                                           <i class="${data.icon} ${data.color} fs-5 me-2"></i>
                                           <strong class="me-auto ${data.color}">${data.title}</strong>
                                          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                                        </div>
                                        <div class="toast-body">
                                          ${data.body}
                                        </div>
                                      </div>
                                    </div>`;

    $('#toastPlace').append(toast);
}



$.ajax({
    url: "/notifications",
    type: "GET",
    success: function (data) {
        $("#notiArea").notification({notifications: data});
    }
});

let audio;

// Play a muted sound on page load to enable automatic playback later
document.addEventListener('DOMContentLoaded', function () {
    playMutedSound();
});


function playMutedSound() {
    // Create a muted audio element
    audio = new Audio('/audio/noti.wav');
    audio.muted = true;
    audio.play().then(() => {
        // Once the muted sound has played, unmute it
        audio.muted = false;
    });
}

function playNotificationSound() {
    // Play the actual notification sound
    audio = new Audio('/audio/noti.wav');
    audio.play();
}

