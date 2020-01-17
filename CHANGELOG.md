# Changelog
All notable changes to this project will be documented in this file.

## [0.4.0] - 2020-01-17

### Added
- `Client.players.deleteLogo()` method (https://docs.api.video/5.1/players/delete-logo)
- `Client.chapters(videoId)` subclient (https://docs.api.video/5.1/chapters)

## [0.3.0] - 2019-12-17

### Added
- `Video.updatedAt` property

### Changed
- `Video.publishedAt` property type from `String` to `Calendar`
- `Info.loadedAt` property type from `String` to `Calendar`
- `Info.endedAt` property type from `String` to `Calendar`
- `PlayerSessionEvent.emittedAt` property type from `String` to `Calendar`
- `Client.videos.upload(String sourceInfo)` -> `Client.videos.upload(File file)`
- `Client.videos.uploadThumbnail(Video video, String thumbnailSource)` -> `Client.videos.uploadThumbnail(Video video, File file)`
- `Client.videos.uploadThumbnailWithTimecode(Video video, String timecode)` -> `Client.videos.uploadThumbnail(Video video, String timecode)`
- `Client.liveStreams.uploadThumbnail(String liveStreamId, String thumbnailSource)` -> `Client.liveStreams.uploadThumbnail(String liveStreamId, File file)`
- `Client.liveStreams.create(LiveStream liveStream)` -> `Client.liveStreams.create(LiveStreamInput liveStreamInput)`
- `Client.players.uploadLogo(String playerId, String logoSource, String link)` -> `Client.players.uploadLogo(String playerId, File file, String link)`
- `Client.captions.upload(String VideoId, String captionSource, String lang)` -> `Client.captions.upload(String VideoId, File file, String lang)`  
- `Client.videos.uploadThumbnail(Video video, File file)` -> `Client.videos.uploadThumbnail(Identifier videoId, File file)`
- `Client.videos.updateThumbnail(Video video, String timecode)` -> `Client.videos.updateThumbnail(Identifier videoId, String timecode)`
- `Client.videos.delete(Video video)` -> `Client.videos.delete(Identifier videoId)`
- `Client.captions.updateDefault(String VideoId, String lang, boolean isDefault)` -> `Client.captions.update(String videoId, CaptionInput captionInput)`
- `Client.captions.getAll(String VideoId)` -> `Client.captions.list(String videoId)`

## [0.2.0] - 2019-09-20

### Changed
- Move UploadProgressListener to domain
- Rename sub-client interfaces to match implementations
- Use interfaces in Client
- Replace PageIterator references with Iterator in domain
- Replace Iterator with Iterable in domain interfaces
