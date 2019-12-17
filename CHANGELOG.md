# Changelog
All notable changes to this project will be documented in this file.

## [0.3.0] - 2019-12-17

### Added
- `Video.updatedAt` property

### Changed
- `Client.videos.upload(String source)` -> `Client.videos.upload(File file)`
- `Client.videos.uploadThumbnail(Video video, String thumbnailSource)` -> `Client.videos.uploadThumbnail(Video video, File file)`
- `Client.videos.uploadThumbnailWithTimecode(Video video, String timecode)` -> `Client.videos.uploadThumbnail(Video video, String timecode)`
- `Client.liveStreams.uploadThumbnail(String liveStreamId, String thumbnailSource)` -> `Client.liveStreams.uploadThumbnail(String liveStreamId, File file)`
- `Client.players.uploadLogo(String playerId, String logoSource, String link)` -> `Client.players.uploadLogo(String playerId, File file, String link)`
- `Client.captions.upload(String VideoId, String captionSource, String lang)` -> `Client.captions.upload(String VideoId, File file, String lang)`  

## [0.2.0] - 2019-09-20

### Changed
- Move UploadProgressListener to domain
- Rename sub-client interfaces to match implementations
- Use interfaces in Client
- Replace PageIterator references with Iterator in domain
- Replace Iterator with Iterable in domain interfaces
